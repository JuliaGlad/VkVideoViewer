package myapplication.android.vkvideoviewer.presentation.videos.video

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import myapplication.android.vkvideoviewer.presentation.videos.video.recycler_view.VideoItemModel

class VideoViewModel: ViewModel() {

    private val _items: MutableStateFlow<MutableList<VideoItemModel>>
            = MutableStateFlow(mutableListOf())
    val items: StateFlow<List<VideoItemModel>> = _items.asStateFlow()

    fun removeAll(){
        _items.value.clear()
    }

    fun addItems(newItems: List<VideoItemModel>) {
        _items.value.addAll(newItems)
    }

}