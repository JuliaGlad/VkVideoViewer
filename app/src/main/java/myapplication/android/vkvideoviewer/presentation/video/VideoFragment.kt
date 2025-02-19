package myapplication.android.vkvideoviewer.presentation.video

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentVideoBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.di.component.fragment.video.DaggerVideoFragmentComponent
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import myapplication.android.vkvideoviewer.presentation.video.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoEffect
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoIntent
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoLocalDI
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoPartialState
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoState
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoStoreFactory
import myapplication.android.vkvideoviewer.presentation.video.recycler_view.VideoItemAdapter
import myapplication.android.vkvideoviewer.presentation.video.recycler_view.VideoItemModel
import javax.inject.Inject

class VideoFragment : MviBaseFragment<
        VideoPartialState,
        VideoIntent,
        VideoState,
        VideoEffect>(R.layout.fragment_video) {

    @Inject
    lateinit var localDI: VideoLocalDI

    private val adapter = VideoItemAdapter()
    private var recyclerItems: MutableList<VideoItemModel> = mutableListOf()
    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!
    private var needUpdate = false
    private var loading = false
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
        if (savedInstanceState == null) {
            store.sendIntent(VideoIntent.Init)
        }
        store.sendIntent(VideoIntent.GetVideos)
    }

    override fun resolveEffect(effect: VideoEffect) {
        when (effect) {
            is VideoEffect.OpenVideoActivity -> TODO()
        }
    }

    override fun render(state: VideoState) {
        with(binding) {
            when (state.ui) {
                is LceState.Content -> {
                    if (!needUpdate) {
                        setLayoutsVisibility(GONE, GONE)
                        errorLayout.buttonLoader.visibility = GONE
                        initRecycler(state.ui.data.items)
                        addRefreshListener()
                        addSearchListener()
                        addScrollToEndListener()
                    } else if (binding.swipeRefreshLayout.isRefreshing){
                        clearAndUpdateRecycler(state.ui.data.items)
                    } else {
                        updateRecycler(state.ui.data.items)
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

    private fun clearAndUpdateRecycler(items: List<VideoUiModel>) {
        recyclerItems.clear()
        val newItems = getRecyclerItemsModels(items)
        recyclerItems.addAll(newItems)
        adapter.notifyDataSetChanged()
        binding.swipeRefreshLayout.isRefreshing = false
        needUpdate = false
        loading = false
    }

    private fun updateRecycler(items: List<VideoUiModel>) {
        val newItems = getRecyclerItemsModels(items)
        val startPosition = recyclerItems.size
        recyclerItems.addAll(newItems)
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
        binding.searchEditText.addTextChangedListener(afterTextChanged = {

        })
    }

    private fun initRecycler(items: List<VideoUiModel>) {
        val newItems = getRecyclerItemsModels(items)
        binding.recyclerView.adapter = adapter
        recyclerItems.addAll(newItems)
        adapter.submitList(recyclerItems)
    }

    private fun getRecyclerItemsModels(items: List<VideoUiModel>): List<VideoItemModel> {
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
                                        title,
                                        views,
                                        downloads
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