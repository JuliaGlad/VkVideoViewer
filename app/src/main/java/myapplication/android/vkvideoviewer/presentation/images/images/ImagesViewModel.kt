package myapplication.android.vkvideoviewer.presentation.images.images

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import myapplication.android.vkvideoviewer.presentation.images.images.recycler_view.ImageItemModel

class ImagesViewModel : ViewModel() {

    private val _items: MutableStateFlow<MutableList<ImageItemModel>>
            = MutableStateFlow(mutableListOf())
    val items: StateFlow<List<ImageItemModel>> = _items.asStateFlow()

    fun removeAll(){
        _items.value.clear()
    }

    fun addItems(newItems: List<ImageItemModel>) {
        _items.value.addAll(newItems)
    }

}