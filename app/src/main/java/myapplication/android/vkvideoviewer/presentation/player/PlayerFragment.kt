package myapplication.android.vkvideoviewer.presentation.player

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.app.Constants
import myapplication.android.vkvideoviewer.databinding.FragmentPlayerBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.di.component.fragment.player.DaggerPlayerFragmentComponent
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerArguments
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerEffect
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerIntent
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerLocalDI
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerPartialState
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerState
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerStoreFactory
import myapplication.android.vkvideoviewer.presentation.player.recycler_view.VideoHorizontalItemAdapter
import myapplication.android.vkvideoviewer.presentation.player.recycler_view.VideoHorizontalItemModel
import javax.inject.Inject

class PlayerFragment : MviBaseFragment<
        PlayerPartialState,
        PlayerIntent,
        PlayerState,
        PlayerEffect>(R.layout.fragment_player) {

    @Inject
    lateinit var localDI: PlayerLocalDI

    private lateinit var player: ExoPlayer
    private val args: PlayerArguments by lazy { getIntentArguments() }

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

    private val adapter = VideoHorizontalItemAdapter()
    private val recyclerItems: MutableList<VideoHorizontalItemModel> = mutableListOf()
    private var _binding: FragmentPlayerBinding? = null
    private var loading = false
    private var needUpdate = false
    private val binding: FragmentPlayerBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerPlayerFragmentComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
        player = createPlayer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(layoutInflater)
        return binding.root
    }

    override val store: MviStore<PlayerPartialState, PlayerIntent, PlayerState, PlayerEffect>
            by viewModels { PlayerStoreFactory(localDI.actor, localDI.reducer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        store.sendIntent(PlayerIntent.GetVideos(args.videoPage, args.videoId))
    }

    private fun createPlayer() = ExoPlayer.Builder(requireContext()).build()

    override fun resolveEffect(effect: PlayerEffect) {
        when (effect) {
            PlayerEffect.FinishActivity -> TODO()
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
                        initPlayer(state.ui.data.qualities.medium.url)
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

    private fun initMainItemData() {
        with(binding) {
            videoTitle.text = args.title
            viewsCount.text = "${args.views}"
            downloadsCount.text = "${args.downloads}"
        }
    }

    private fun initPlayer(url: String) {
        binding.playerView.player = player
        loadVideo(url)
    }

    private fun loadVideo(url: String) {
        val mediaItem = MediaItem.fromUri(Uri.parse(url))
        player.setMediaItem(mediaItem)
        player.prepare()
        player.playWhenReady = true
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
                                    TODO("Change to video")
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
        _binding = null
    }

}