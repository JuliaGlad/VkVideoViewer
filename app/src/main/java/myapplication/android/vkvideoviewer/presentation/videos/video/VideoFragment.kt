package myapplication.android.vkvideoviewer.presentation.videos.video

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentVideoBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.main.MainActivity
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.videos.video.di.DaggerVideoFragmentComponent
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoEffect
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoIntent
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoLocalDI
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoPartialState
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoState
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoStoreFactory
import myapplication.android.vkvideoviewer.presentation.videos.video.recycler_view.VideoItemAdapter
import myapplication.android.vkvideoviewer.presentation.videos.video.recycler_view.VideoItemModel
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class VideoFragment : MviBaseFragment<
        VideoPartialState,
        VideoIntent,
        VideoState,
        VideoEffect>(R.layout.fragment_video) {

    @Inject
    lateinit var localDI: VideoLocalDI

    private val adapter: VideoItemAdapter = VideoItemAdapter()
    private var recyclerItems: MutableList<VideoItemModel> = mutableListOf()
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<VideoViewModel>()
    private var needUpdate: Boolean = false
    private var loading: Boolean = false
    override val store: MviStore<VideoPartialState, VideoIntent, VideoState, VideoEffect>
            by viewModels {
                VideoStoreFactory(localDI.actor, localDI.reducer)
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerVideoFragmentComponent.factory().create(appComponent).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.items.value.isEmpty()) {
            store.sendIntent(VideoIntent.GetVideos)
        } else {
            viewModel.addItems(viewModel.items.value)
            binding.recyclerView.adapter = adapter
            adapter.submitList(recyclerItems)
        }
    }

    override fun resolveEffect(effect: VideoEffect) {
        when (effect) {
            is VideoEffect.OpenVideoActivity ->{
                with(effect) {
                    (activity as MainActivity).openPlayerActivity(
                        videoId = videoId,
                        videoPage = page,
                        title = title,
                        views = views,
                        thumbnail = thumbnail,
                        downloads = downloads
                    )
                }
            }
            is VideoEffect.OpenDownloadingMenu -> TODO()
        }
    }

    override fun render(state: VideoState) {
        with(binding) {
            when (state.ui) {
                is LceState.Content -> {
                    if (!needUpdate) {
                        setLayoutsVisibility(GONE, GONE)
                        errorLayout.buttonLoader.visibility = GONE
                        initRecycler(state.ui.data.items, state.page)
                        addRefreshListener()
                        addSearchListener()
                        addScrollToEndListener()
                    } else if (binding.swipeRefreshLayout.isRefreshing) {
                        clearAndUpdateRecycler(state.ui.data.items, state.page)
                    } else {
                        updateRecycler(state.ui.data.items, state.page)
                    }
                }

                is LceState.Error -> {
                    Log.e("Error Video", state.ui.throwable.message.toString())
                    setLayoutsVisibility(GONE, VISIBLE)
                    errorLayout.button.setOnClickListener {
                        store.sendIntent(VideoIntent.GetVideos)
                        errorLayout.buttonLoader.visibility = VISIBLE
                    }
                }

                LceState.Loading -> {
                    setLayoutsVisibility(VISIBLE, GONE)
                    errorLayout.buttonLoader.visibility = GONE
                }
            }
        }
    }

    private fun clearAndUpdateRecycler(items: List<VideoUiModel>, page: Int) {
        recyclerItems.clear()
        viewModel.removeAll()
        val newItems = getRecyclerItemsModels(items, page)
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        adapter.notifyDataSetChanged()
        binding.swipeRefreshLayout.isRefreshing = false
        needUpdate = false
        loading = false
    }

    private fun updateRecycler(items: List<VideoUiModel>, page: Int) {
        val newItems = getRecyclerItemsModels(items, page)
        val startPosition = recyclerItems.size
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        adapter.notifyItemRangeInserted(startPosition, newItems.size)
        needUpdate = false
        loading = false
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
                    val query = binding.searchEditText.text.toString()
                    if (query.isEmpty()) store.sendIntent(VideoIntent.GetVideos)
                    else store.sendIntent(VideoIntent.GetVideosByQuery(query))
                }
            })
        }
    }

    private fun addRefreshListener() {
        with(binding) {
            binding.swipeRefreshLayout.setOnRefreshListener {
                needUpdate = true
                loading = true
                if (searchEditText.text?.isEmpty() == true) store.sendIntent(VideoIntent.GetVideos)
                else store.sendIntent(VideoIntent.GetVideosByQuery(searchEditText.text.toString()))
            }
        }
    }

    private fun addSearchListener() {
        binding.searchEditText.addTextChangedListener {
            val query = binding.searchEditText.text.toString()
            store.sendIntent(VideoIntent.Init)
            recyclerItems.clear()
            adapter.notifyDataSetChanged()
            if (query.isNotEmpty())store.sendIntent(VideoIntent.GetVideosByQuery(query))
            else store.sendIntent(VideoIntent.GetVideos)
        }
    }

    private fun initRecycler(items: List<VideoUiModel>, page: Int) {
        val newItems = getRecyclerItemsModels(items, page)
        binding.recyclerView.adapter = adapter
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        adapter.submitList(recyclerItems)
    }

    private fun getRecyclerItemsModels(items: List<VideoUiModel>, page: Int): List<VideoItemModel> {
        val newItems = mutableListOf<VideoItemModel>()
        items.forEachIndexed { index, videoUiModel ->
            with(videoUiModel) {
                newItems.add(
                    VideoItemModel(
                        id = index,
                        videoId = videoUiModel.id,
                        title = title,
                        duration = duration,
                        views = views,
                        downloads = downloads,
                        thumbnail = thumbnail,
                        itemClickListener = object : ClickListener {
                            override fun onClick() {
                                store.sendEffect(
                                    VideoEffect.OpenVideoActivity(
                                        videoId = videoUiModel.id,
                                        page = page,
                                        title = title,
                                        views = views,
                                        thumbnail = thumbnail,
                                        downloads = downloads
                                    )
                                )
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
        return newItems
    }

    private fun FragmentVideoBinding.setLayoutsVisibility(loading: Int, error: Int) {
        loadingLayout.root.visibility = loading
        errorLayout.root.visibility = error
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}