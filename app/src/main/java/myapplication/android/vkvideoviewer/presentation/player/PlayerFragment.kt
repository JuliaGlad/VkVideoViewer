package myapplication.android.vkvideoviewer.presentation.player

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.annotation.OptIn
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.common.util.Util
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.FragmentPlayerBinding
import myapplication.android.vkvideoviewer.databinding.PlayerCustomControllersBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.di.component.fragment.player.DaggerPlayerFragmentComponent
import myapplication.android.vkvideoviewer.presentation.dialogs.OptionsDialogFragment
import myapplication.android.vkvideoviewer.presentation.dialogs.VideoQualityDialogFragment
import myapplication.android.vkvideoviewer.presentation.dialogs.VideoSpeedDialogFragment
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.DialogDismissedListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerArguments
import myapplication.android.vkvideoviewer.presentation.player.model.VideoQualitiesUiList
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerEffect
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerIntent
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerLocalDI
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerPartialState
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerState
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerStoreFactory
import myapplication.android.vkvideoviewer.presentation.player.recycler_view.VideoHorizontalItemAdapter
import myapplication.android.vkvideoviewer.presentation.player.recycler_view.VideoHorizontalItemModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min


@UnstableApi
class PlayerFragment : MviBaseFragment<
        PlayerPartialState,
        PlayerIntent,
        PlayerState,
        PlayerEffect>(R.layout.fragment_player) {

    @Inject
    lateinit var localDI: PlayerLocalDI

    private lateinit var player: ExoPlayer
    private val args: PlayerArguments by lazy { getIntentArguments() }

    private val adapter = VideoHorizontalItemAdapter()
    private val recyclerItems: MutableList<VideoHorizontalItemModel> = mutableListOf()

    private var _binding: FragmentPlayerBinding? = null
    private val binding: FragmentPlayerBinding
        get() = _binding!!
    private var _customControllerBinding: PlayerCustomControllersBinding? = null
    private val customControllerBinding get() = _customControllerBinding!!

    private fun getIntentArguments(): PlayerArguments {
        var arguments: PlayerArguments
        with(activity?.intent!!) {
            arguments = PlayerArguments(
                videoId = getIntExtra(Constants.VIDEO_ID, 0),
                videoPage = getIntExtra(Constants.VIDEO_PAGE, 0),
                title = getStringExtra(Constants.VIDEO_TITLE)!!,
                views = getIntExtra(Constants.VIDEO_VIEWS, 0),
                downloads = getIntExtra(Constants.VIDEO_DOWNLOADS, 0)
            )
        }
        return arguments
    }

    private val handler = Handler(Looper.getMainLooper())
    private var loading = false
    private var needUpdate = false
    private var isFullscreen = false
    private var currentQuality = Constants.MEDIUM_ID
    private var currentSpeed = Constants.NORMAL_ID
    private val videoQualities = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerPlayerFragmentComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)

        player = ExoPlayer.Builder(requireContext()).build()

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(layoutInflater)
        _customControllerBinding = PlayerCustomControllersBinding
            .bind(binding.playerView.findViewById(R.id.player_controls))

        return binding.root
    }

    override val store: MviStore<PlayerPartialState, PlayerIntent, PlayerState, PlayerEffect>
            by viewModels { PlayerStoreFactory(localDI.actor, localDI.reducer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(PlayerIntent.GetVideos(args.videoPage, args.videoId))
    }

    override fun resolveEffect(effect: PlayerEffect) {
        when (effect) {
            PlayerEffect.FinishActivity -> {

            }
        }
    }

    override fun render(state: PlayerState) {
        with(binding) {
            when (state.ui) {
                is LceState.Content -> {
                    setLayoutVisibility(GONE, GONE)
                    if (!needUpdate) {
                        initRecycler(state.ui.data.videos.items)
                        initMainItemData()
                        addScrollToEndListener()
                        setVideoQualities(state.ui.data.qualities)
                        initPlayer()
                    } else {
                        updateRecycler(state.ui.data.videos.items)
                    }
                }

                is LceState.Error -> {
                    setLayoutVisibility(GONE, VISIBLE)
                    Log.e("Error Player", state.ui.throwable.message.toString())
                    errorLayout.button.setOnClickListener {
                        store.sendIntent(PlayerIntent.GetVideos(args.videoPage, args.videoId))
                    }
                }

                LceState.Loading -> setLayoutVisibility(VISIBLE, GONE)
            }
        }
    }

    private fun setVideoQualities(qualityItem: VideoQualitiesUiList) {
        videoQualities[Constants.TINY_ID] = qualityItem.tiny.url
        videoQualities[Constants.SMALL_ID] = qualityItem.small.url
        videoQualities[Constants.MEDIUM_ID] = qualityItem.medium.url
    }

    private fun initMainItemData() {
        with(binding) {
            videoTitle.text = args.title
            viewsCount.text = "${args.views}"
            downloadsCount.text = "${args.downloads}"
        }
    }

    private fun initPlayer() {
        val userAgent = Util.getUserAgent(requireContext(), activity?.applicationInfo!!.name)
        val dataSource = DefaultHttpDataSource.Factory().setUserAgent(userAgent)
        val mediaSource = ProgressiveMediaSource.Factory(dataSource)
            .createMediaSource(MediaItem.fromUri(videoQualities[currentQuality]!!))

        binding.playerView.player = player
        player.setPlaybackSpeed(currentSpeed)
        player.setMediaSource(mediaSource)

        loadVideo(0)
        initVideoControlsButtons()
    }

    private fun initVideoControlsButtons() {
        with(customControllerBinding) {
            videoTitlePlayer.text = args.title
            buttonPlayPause
                .setOnClickListener {
                    if (player.isPlaying) {
                        player.pause()
                        buttonPlayPause.setImageResource(R.drawable.ic_play_arrow)
                    } else {
                        player.play()
                        buttonPlayPause.setImageResource(R.drawable.ic_pause)
                    }
                }

            buttonPlayAgain.setOnClickListener {
                player.seekTo(0)
                player.playWhenReady = true
                buttonPlayAgain.visibility = GONE
                buttonPlayPause.visibility = VISIBLE
            }

            buttonMoveForward.setOnClickListener {
                player.seekTo(min(player.currentPosition + 10000, player.duration))
            }
            buttonMoveBack.setOnClickListener {
                player.seekTo(max(player.currentPosition - 10000, 0))
            }
            buttonFullscreen.setOnClickListener {
                toggleFullscreen()
            }
            buttonOptions.setOnClickListener { showOptionsDialog() }
        }
    }

    private fun loadVideo(currentPosition: Long) {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoQualities[currentQuality]))
        player.setMediaItem(mediaItem)
        player.prepare()
        startProgressUpdater(currentPosition)
        player.playWhenReady = true
        if (currentPosition != 0L) player.seekTo(currentPosition)
        addProgressBarListener()
    }

    private fun addProgressBarListener() {

        player.addListener(object : Player.Listener{
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED){
                    with(customControllerBinding) {
                        buttonPlayPause.visibility = GONE
                        buttonPlayAgain.visibility = VISIBLE
                    }
                }
            }
        })

    }

    private fun startProgressUpdater(currentPosition: Long) {
        with(customControllerBinding) {
            handler.post(object : Runnable {
                override fun run() {
                    val position = player.currentPosition
                    val duration = player.duration
                    timer.text = formatTime(position)

                    timeBar.setPosition(position)
                    timeBar.setDuration(duration)

                    handler.postDelayed(this, 1000)
                }
            })
        }
    }

    private fun toggleFullscreen() {
        val activity = requireActivity()
        val window = activity.window

        if (!isFullscreen) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.apply {
                    hide(WindowInsets.Type.systemBars())
                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        )
            }
            binding.playerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

        } else {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.show(WindowInsets.Type.systemBars())
            } else {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
            binding.playerView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }

        isFullscreen = !isFullscreen
    }

    private fun formatTime(time: Long): CharSequence {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(time)
        val seconds = (TimeUnit.MILLISECONDS.toSeconds(time) % 60)
        val minutesStr =
            if (minutes < 10) "0$minutes"
            else "$minutes"
        val secondsStr =
            if (seconds < 10) "0$seconds"
            else "$seconds"

        return "$minutesStr : $secondsStr"
    }

    private fun showOptionsDialog() {
        val dialogFragment = OptionsDialogFragment()
        dialogFragment.show(activity?.supportFragmentManager!!, "OPTIONS_DIALOG")
        dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
            override fun handleDialogClose(args: Bundle?) {
                if (args != null) {
                    val option = args.getString(Constants.OPTIONS)
                    if (option != null) {
                        when (option) {
                            Constants.OPTION_SPEED -> showSpeedDialog()
                            Constants.OPTION_QUALITY -> showQualityDialog()
                        }
                    }
                }
            }
        })
    }

    private fun showQualityDialog() {
        val dialogFragment = VideoQualityDialogFragment()
        dialogFragment.show(activity?.supportFragmentManager!!, "OPTIONS_DIALOG")
        dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
            override fun handleDialogClose(args: Bundle?) {
                if (args != null) {
                    val quality = args.getString(Constants.QUALITY)
                    if (quality != null) {
                        if (quality != currentQuality) {
                            val currentPosition = player.currentPosition
                            currentQuality = quality
                            loadVideo(currentPosition)
                        }
                    }
                }
            }
        })
    }

    private fun showSpeedDialog() {
        val dialogFragment = VideoSpeedDialogFragment()
        dialogFragment.show(activity?.supportFragmentManager!!, "OPTIONS_DIALOG")
        dialogFragment.setDialogDismissedListener(object : DialogDismissedListener {
            override fun handleDialogClose(args: Bundle?) {
                if (args != null) {
                    val speed = args.getFloat(Constants.SPEED)
                    if (speed != currentSpeed) {
                        currentSpeed = speed
                        player.setPlaybackSpeed(currentSpeed)
                    }
                }
            }
        })
    }

    private fun updateRecycler(items: List<VideoUiModel>) {
        val newItems = getRecyclerItemsModels(items)
        val startPosition = recyclerItems.size
        recyclerItems.addAll(newItems)
        adapter.notifyItemRangeInserted(startPosition, newItems.size)
        loading = false
        needUpdate = false
    }

    private fun initRecycler(items: List<VideoUiModel>) {
        val newItems = getRecyclerItemsModels(items)
        recyclerItems.addAll(newItems)
        binding.recyclerView.adapter = adapter
        adapter.submitList(newItems)
    }

    private fun getRecyclerItemsModels(items: List<VideoUiModel>): List<VideoHorizontalItemModel> {
        val newItems = mutableListOf<VideoHorizontalItemModel>()
        items.forEachIndexed { index, videoUiModel ->
            if (videoUiModel.id != args.videoId) {
                with(videoUiModel) {
                    newItems.add(
                        VideoHorizontalItemModel(
                            id = index,
                            videoId = videoUiModel.id,
                            title = title,
                            duration = duration,
                            views = views,
                            downloads = downloads,
                            thumbnail = thumbnail,
                            itemClickListener = object : ClickListener {
                                override fun onClick() {

                                }
                            },
                            actionClickListener = object : ClickListener {
                                override fun onClick() {
                                    TODO("open menu to downloading")
                                }
                            }
                        )
                    )
                }
            }
        }
        return newItems
    }

    private fun FragmentPlayerBinding.setLayoutVisibility(loading: Int, error: Int) {
        loadingLayout.root.visibility = loading
        errorLayout.root.visibility = error
    }

    private fun addScrollToEndListener() {
        with(binding.recyclerView) {
            binding.recyclerView.addOnScrollListener(object :
                LinearPaginationScrollListener(layoutManager as LinearLayoutManager) {
                override fun isLastPage(): Boolean = needUpdate

                override fun isLoading(): Boolean = loading

                override fun loadMoreItems() {
                    needUpdate = true
                    loading = true
                    store.sendIntent(PlayerIntent.GetNewVideos)
                }
            })
        }
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }

}