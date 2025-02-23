package myapplication.android.vkvideoviewer.presentation.images.images

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import myapplication.android.vkvideoviewer.R
import myapplication.android.vkvideoviewer.databinding.FragmentImagesBinding
import myapplication.android.vkvideoviewer.di.DaggerAppComponent
import myapplication.android.vkvideoviewer.presentation.images.images.di.DaggerImageFragmentComponent
import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiModel
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesEffect
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesIntent
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesLocalDI
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesPartialState
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesState
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesStoreFactory
import myapplication.android.vkvideoviewer.presentation.images.images.recycler_view.ImageItemAdapter
import myapplication.android.vkvideoviewer.presentation.images.images.recycler_view.ImageItemModel
import myapplication.android.vkvideoviewer.presentation.listener.ClickListener
import myapplication.android.vkvideoviewer.presentation.listener.LinearPaginationScrollListener
import myapplication.android.vkvideoviewer.presentation.main.MainActivity
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviBaseFragment
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore
import javax.inject.Inject

class ImagesFragment : MviBaseFragment<
        ImagesPartialState,
        ImagesIntent,
        ImagesState,
        ImagesEffect>(R.layout.fragment_images) {

    private var category: String = ""
    private val adapter = ImageItemAdapter()
    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ImagesViewModel>()
    private val recyclerItems = mutableListOf<ImageItemModel>()
    private var needUpdate = false
    private var loading = false

    @Inject
    lateinit var localDI: ImagesLocalDI

    override val store: MviStore<ImagesPartialState, ImagesIntent, ImagesState, ImagesEffect>
            by viewModels { ImagesStoreFactory(localDI.actor, localDI.reducer) }

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = DaggerAppComponent.factory().create(requireContext())
        DaggerImageFragmentComponent.factory().create(appComponent).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.items.value.isEmpty()) {
            sendIntent()
        } else {
            viewModel.addItems(viewModel.items.value)
            binding.recyclerView.adapter = adapter
            adapter.submitList(recyclerItems)
        }
    }

    private fun sendIntent() {
        val categories = resources.getStringArray(R.array.image_categories)
        if (category == categories[0]) store.sendIntent(ImagesIntent.GetImages)
        else store.sendIntent(ImagesIntent.GetImagesByCategory(category))
    }

    override fun resolveEffect(effect: ImagesEffect) {
        when (effect) {
            is ImagesEffect.OpenFullScreenImage ->
                (activity as MainActivity).openImageFullScreenActivity(effect.url)
        }
    }

    override fun render(state: ImagesState) {
        with(binding) {
            when (state.ui) {
                is LceState.Content -> {
                    val data = state.ui.data.items
                    if (!needUpdate) {
                        initRecycler(data)
                        addRefreshListener()
                        addScrollToEndListener()
                    } else if (swipeRefreshLayout.isRefreshing) refreshRecycler(data)
                    else updateRecycler(data)
                    setVisibility(GONE, GONE)
                }

                is LceState.Error -> {
                    setVisibility(GONE, VISIBLE)
                    Log.e("Images error", state.ui.throwable.message.toString())
                    errorLayout.buttonLoader.setOnClickListener {
                        errorLayout.buttonLoader.visibility = VISIBLE
                        sendIntent()
                    }
                }

                LceState.Loading -> setVisibility(VISIBLE, GONE)
            }
        }
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
                    sendIntent()
                }
            })
        }
    }

    private fun addRefreshListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            needUpdate = true
            loading = true
            sendIntent()
        }
    }

    private fun updateRecycler(data: List<ImageUiModel>) {
        val newItems = getRecyclerItems(data)
        val startPosition = recyclerItems.size
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        adapter.notifyItemRangeInserted(startPosition, newItems.size)
        needUpdate = false
        loading = false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshRecycler(items: List<ImageUiModel>) {
        recyclerItems.clear()
        viewModel.removeAll()
        val newItems = getRecyclerItems(items)
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        adapter.notifyDataSetChanged()
        binding.swipeRefreshLayout.isRefreshing = false
        needUpdate = false
        loading = false
    }

    private fun initRecycler(items: List<ImageUiModel>) {
        val newItems = getRecyclerItems(items)
        recyclerItems.addAll(newItems)
        viewModel.addItems(newItems)
        binding.recyclerView.adapter = adapter
        adapter.submitList(recyclerItems)
    }

    private fun getRecyclerItems(items: List<ImageUiModel>): List<ImageItemModel> {
        val newItems = mutableListOf<ImageItemModel>()
        items.forEachIndexed { index, imageUiModel ->
            with(imageUiModel) {
                newItems.add(
                    ImageItemModel(
                        id = index,
                        imageId = imageId,
                        title = title,
                        views = views,
                        downloads = downloads,
                        thumbnail = previewUrl,
                        object : ClickListener {
                            override fun onClick() {
                                store.sendEffect(ImagesEffect.OpenFullScreenImage(largeImageUrl))
                            }
                        },
                        object : ClickListener {
                            override fun onClick() {
                                TODO("Add to saved")
                            }
                        }
                    )
                )
            }
        }
        return newItems
    }

    private fun setVisibility(loading: Int, error: Int) {
        with(binding) {
            errorLayout.buttonLoader.visibility = GONE
            loadingLayout.root.visibility = loading
            errorLayout.root.visibility = error
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun getInstance(category: String) =
            ImagesFragment().apply {
                this.category = category
            }
    }

}