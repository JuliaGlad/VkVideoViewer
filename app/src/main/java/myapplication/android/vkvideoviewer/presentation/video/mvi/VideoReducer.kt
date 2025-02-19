package myapplication.android.vkvideoviewer.presentation.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviReducer
import myapplication.android.vkvideoviewer.presentation.video.model.VideoUiList

class VideoReducer: MviReducer<
        VideoPartialState,
        VideoState> {
    override fun reduce(
        prevState: VideoState,
        partialState: VideoPartialState
    ): VideoState =
        when(partialState){
            is VideoPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.ui)
            is VideoPartialState.Error -> updateError(prevState, partialState.throwable)
            VideoPartialState.Init -> updateInit(prevState)
            VideoPartialState.Loading -> updateLoading(prevState)
        }

    private fun updateInit(prevState: VideoState) = prevState.copy(page = 0)

    private fun updateDataLoaded(prevState: VideoState, data: VideoUiList) =
        prevState.copy(ui = LceState.Content(data), page = prevState.page + 1)

    private fun updateLoading(prevState: VideoState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: VideoState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))
}