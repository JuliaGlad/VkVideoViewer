package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiList
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviReducer

class ImagesReducer : MviReducer<ImagesPartialState, ImagesState> {

    override fun reduce(
        prevState: ImagesState,
        partialState: ImagesPartialState
    ): ImagesState =
        when (partialState) {
            is ImagesPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is ImagesPartialState.Error -> updateError(prevState, partialState.throwable)
            ImagesPartialState.Init -> updateInit(prevState)
            ImagesPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateInit(prevState: ImagesState) = prevState.copy(page = 0)

    private fun updateDataLoaded(prevState: ImagesState, data: ImageUiList) =
        prevState.copy(ui = LceState.Content(data), page = prevState.page + 1)

    private fun updateLoading(prevState: ImagesState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: ImagesState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}