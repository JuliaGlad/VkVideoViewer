package myapplication.android.vkvideoviewer.presentation.videos.player

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import myapplication.android.vkvideoviewer.presentation.videos.player.recycler_view.VideoHorizontalItemModel

class PlayerViewModel: ViewModel() {

    private val _items: MutableStateFlow<MutableList<VideoHorizontalItemModel>>
    = MutableStateFlow(mutableListOf())
    val items: StateFlow<List<VideoHorizontalItemModel>> = _items.asStateFlow()

    fun removeItem(item: VideoHorizontalItemModel){
        _items.value.remove(item)
    }

    fun addItems(newItems: List<VideoHorizontalItemModel>) {
        _items.value.addAll(newItems)
    }
}