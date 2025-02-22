package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ImagesStoreFactory(
    private val actor: ImagesActor,
    private val reducer: ImagesReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagesStore(reducer, actor) as T
    }
}