package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiList
import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState

sealed interface ImagesPartialState: MviPartialState {

    data object Init: ImagesPartialState

    data object Loading: ImagesPartialState

    data class DataLoaded(val ui: ImageUiList): ImagesPartialState

    data class Error(val throwable: Throwable): ImagesPartialState

}