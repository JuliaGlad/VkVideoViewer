package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.presentation.model.VideoUiList
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviReducer
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerResult

class PlayerReducer: MviReducer<
        PlayerPartialState,
        PlayerState> {
    override fun reduce(
        prevState: PlayerState,
        partialState: PlayerPartialState
    ): PlayerState =
        when(partialState){
            is PlayerPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is PlayerPartialState.Error -> updateError(prevState, partialState.throwable)
            PlayerPartialState.Loading -> updateLoading(prevState)
            is PlayerPartialState.NewVideosLoaded -> updateNewVideosLoaded(prevState, partialState.videos)
        }

    private fun updateNewVideosLoaded(prevState: PlayerState, ui: VideoUiList): PlayerState{
        (prevState.ui as LceState.Content).data.videos = ui
        return prevState.copy()
    }

    private fun updateLoading(prevState: PlayerState) =
        prevState.copy(ui = LceState.Loading)

    private fun updateError(prevState: PlayerState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable))

    private fun updateDataLoaded(prevState: PlayerState, ui: PlayerResult) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1)
}