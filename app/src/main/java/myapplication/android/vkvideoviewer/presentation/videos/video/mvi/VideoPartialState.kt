package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList

sealed interface VideoPartialState: MviPartialState {

    data object Init: VideoPartialState

    data object Loading: VideoPartialState

    class DataLoaded(val ui: VideoUiList): VideoPartialState

    class DataByQueryLoaded(val query: String): VideoPartialState

    class Error(val throwable: Throwable): VideoPartialState
}