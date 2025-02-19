package myapplication.android.vkvideoviewer.presentation.video.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class VideoStoreFactory(
    private val actor: VideoActor,
    private val reducer: VideoReducer
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoStore(reducer, actor) as T
    }

}