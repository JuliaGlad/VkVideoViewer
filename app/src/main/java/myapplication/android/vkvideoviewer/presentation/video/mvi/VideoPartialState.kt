package myapplication.android.vkvideoviewer.presentation.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState
import myapplication.android.vkvideoviewer.presentation.model.VideoUiList

sealed interface VideoPartialState: MviPartialState {

    data object Init: VideoPartialState

    data object Loading: VideoPartialState

    data class DataLoaded(val ui: VideoUiList): VideoPartialState

    data class Error(val throwable: Throwable): VideoPartialState
}