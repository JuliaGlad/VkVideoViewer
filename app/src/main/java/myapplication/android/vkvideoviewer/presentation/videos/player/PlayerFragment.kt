package myapplication.android.vkvideoviewer.presentation.videos.player

import android.content.pm.ActivityInfo
import android.content.res.Configuration
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
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentPlayerBinding
import myapplication.android.vkvideoviewer.databinding.PlayerCustomControllersBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.presentation.dialogs.OptionsDialogFragment
import myapplication.android.vkvideoviewer.presentation.dialogs.VideoQualityDialogFragment
import myapplication.android.vkvideoviewer.presentation.dialogs.VideoSpeedDialogFragment
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.DialogDismissedListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.videos.player.di.DaggerPlayerFragmentComponent
import myapplication.android.vkvideoviewer.presentation.videos.player.model.PlayerArguments
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualitiesUiList
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerEffect
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerIntent
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerLocalDI
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerPartialState
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerState
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerStoreFactory
import myapplication.android.vkvideoviewer.presentation.videos.player.recycler_view.VideoHorizontalItemAdapter
import myapplication.android.vkvideoviewer.presentation.videos.player.recycler_view.VideoHorizontalItemModel
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
    private lateinit var args: PlayerArguments

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
                videoId = getIntExtra(VIDEO_ID, 0),
                videoPage = getIntExtra(VIDEO_PAGE, 0),
                title = getStringExtra(VIDEO_TITLE)!!,
                views = getIntExtra(VIDEO_VIEWS, 0),
                downloads = getIntExtra(VIDEO_DOWNLOADS, 0)
            )
        }
        return arguments
    }

    private val handler = Handler(Looper.getMainLooper())
    private var loading = false
    private var needUpdate = false
    private var isFullscreen = false
    private var previousOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    private var currentQuality = MEDIUM_ID
    private var currentSpeed = NORMAL_ID
    private val videoQualities = mutableMapOf<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerPlayerFragmentComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)

        player = ExoPlayer.Builder(requireContext()).build()
        args = getIntentArguments()

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
                (activity as PlayerActivity).finish()
            }

            is PlayerEffect.OpenAnotherVideo -> {
                with(effect.playerArguments){
                    binding.loadingLayout.root.visibility = VISIBLE
                    args = this
                    store.sendIntent(PlayerIntent.GetVideoQuality(args.videoPage, args.videoId))
                    initMainItemData()
                    for (i in recyclerItems){
                        val index = recyclerItems.indexOf(i)
                        if (i.videoId == args.videoId){
                            recyclerItems.removeAt(index)
                            adapter.notifyItemRemoved(index)
                            break
                        }
                    }
                    customControllerBinding.videoTitlePlayer.text = title
                    customControllerBinding.buttonPlayAgain.visibility = GONE
                    customControllerBinding.buttonPlayPause.visibility = VISIBLE
                }
            }
        }
    }

    override fun render(state: PlayerState) {
        with(binding) {
            when (state.ui) {
                is LceState.Content -> {
                    binding.errorLayout.buttonLoader.visibility = GONE
                    setLayoutVisibility(GONE, GONE)
                    if (!state.isNewVideo) {
                        if (!needUpdate) {
                            initRecycler(state.ui.data.videos.items, state.page)
                            initMainItemData()
                            addScrollToEndListener()
                            setVideoQualities(state.ui.data.qualities)
                            initPlayer()
                        } else {
                            updateRecycler(state.ui.data.videos.items, state.page)
                        }
                    } else {
                        setVideoQualities(state.ui.data.qualities)
                        currentQuality = MEDIUM_ID
                        currentSpeed = NORMAL_ID
                        loadVideo()
                        binding.loadingLayout.root.visibility = GONE
                    }
                }

                is LceState.Error -> {
                    setLayoutVisibility(GONE, VISIBLE)
                    binding.errorLayout.buttonLoader.visibility = GONE
                    Log.e("Error Player", state.ui.throwable.message.toString())
                    errorLayout.button.setOnClickListener {
                        binding.errorLayout.buttonLoader.visibility = VISIBLE
                        store.sendIntent(PlayerIntent.GetVideos(args.videoPage, args.videoId))
                    }
                }

                LceState.Loading -> {
                    binding.errorLayout.buttonLoader.visibility = GONE
                    setLayoutVisibility(VISIBLE, GONE)
                }
            }
        }
    }

    private fun setVideoQualities(qualityItem: VideoQualitiesUiList) {
        videoQualities[TINY_ID] = qualityItem.tiny.url
        videoQualities[SMALL_ID] = qualityItem.small.url
        videoQualities[MEDIUM_ID] = qualityItem.medium.url
    }

    private fun initMainItemData() {
        with(binding) {
            videoTitle.text = args.title
            viewsCount.text = "${args.views}"
            downloadsCount.text = "${args.downloads}"
        }
    }

    private fun initPlayer() {
        binding.playerView.player = player
        player.setPlaybackSpeed(currentSpeed)
        loadVideo()
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

    private fun loadVideo() {
        val mediaItem = MediaItem.fromUri(Uri.parse(videoQualities[currentQuality]))
        player.setMediaItem(mediaItem)
        player.prepare()
        startProgressUpdater()
        player.playWhenReady = true
        addProgressBarListener()
    }

    private fun addProgressBarListener() {
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == Player.STATE_ENDED) {
                    with(customControllerBinding) {
                        buttonPlayPause.visibility = GONE
                        buttonPlayAgain.visibility = VISIBLE
                    }
                }
            }
        })
    }

    private fun startProgressUpdater() {
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
                    val option = args.getString(OPTIONS)
                    if (option != null) {
                        when (option) {
                            OPTION_SPEED -> showSpeedDialog()
                            OPTION_QUALITY -> showQualityDialog()
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
                    val quality = args.getString(QUALITY)
                    if (quality != null) {
                        if (quality != currentQuality) {
                            currentQuality = quality
                            loadVideo()
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
                    val speed = args.getFloat(SPEED)
                    if (speed != currentSpeed) {
                        currentSpeed = speed
                        player.setPlaybackSpeed(currentSpeed)
                    }
                }
            }
        })
    }

    private fun updateRecycler(items: List<VideoUiModel>, page: Int) {
        val newItems = getRecyclerItemsModels(items, page)
        val startPosition = recyclerItems.size
        recyclerItems.addAll(newItems)
        adapter.notifyItemRangeInserted(startPosition, newItems.size)
        loading = false
        needUpdate = false
    }

    private fun initRecycler(items: List<VideoUiModel>, page: Int) {
        val newItems = getRecyclerItemsModels(items, page)
        recyclerItems.addAll(newItems)
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun getRecyclerItemsModels(items: List<VideoUiModel>, page: Int): List<VideoHorizontalItemModel> {
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
                                    store.sendEffect(PlayerEffect.OpenAnotherVideo(
                                        PlayerArguments(
                                            videoId = videoUiModel.id,
                                            videoPage = page,
                                            title = title,
                                            views = views,
                                            downloads = downloads
                                        )
                                    ))
                                }
                            },
                            actionClickListener = object : ClickListener {
                                override fun onClick() {
                                    TODO("open menu to saving")
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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        checkScreenOrientation()
    }

    private fun checkScreenOrientation() {
        val orientation = resources.configuration.orientation
        if (orientation != previousOrientation) {
            toggleFullscreen()
            previousOrientation = orientation
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

    companion object {
        const val VIDEO_ID = "VideoId"
        const val VIDEO_TITLE = "VideoTitle"
        const val VIDEO_VIEWS = "VideoViews"
        const val VIDEO_DOWNLOADS = "VideoDownloads"
        const val VIDEO_PAGE = "VideoPage"
        const val TINY_ID = "270р"
        const val SMALL_ID = "360р"
        const val MEDIUM_ID = "720р"
        const val NORMAL_ID = 1f
        const val OPTIONS = "OptionsId"
        const val QUALITY = "QualityId"
        const val SPEED = "SpeedId"
        const val OPTION_QUALITY = "OptionQuality"
        const val OPTION_SPEED = "OptionSpeed"
    }
}