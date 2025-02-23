package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import android.util.Log
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviReducer
import myapplication.android.vkvideoviewer.presentation.videos.player.model.PlayerResult
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualitiesUiList

class PlayerReducer : MviReducer<
        PlayerPartialState,
        PlayerState> {
    override fun reduce(
        prevState: PlayerState,
        partialState: PlayerPartialState
    ): PlayerState =
        when (partialState) {
            is PlayerPartialState.DataLoaded -> updateDataLoaded(prevState, partialState.data)
            is PlayerPartialState.Error -> updateError(prevState, partialState.throwable)
            PlayerPartialState.Loading -> updateLoading(prevState)
            is PlayerPartialState.NewVideosLoaded -> updateNewVideosLoaded(
                prevState,
                partialState.videos
            )

            is PlayerPartialState.VideoQualitiesLoaded -> updateVideoQualities(
                prevState,
                partialState.qualities
            )
        }

    private fun updateNewVideosLoaded(prevState: PlayerState, ui: VideoUiList): PlayerState {
        (prevState.ui as LceState.Content).data.videos = ui
        val state = PlayerState(
            ui = LceState.Content(
                PlayerResult(
                    prevState.ui.data.qualities,
                    ui,

                ),
            ),
            isNewVideo = false,
            page = prevState.page
        )
        return state
    }

    private fun updateVideoQualities(
        prevState: PlayerState,
        ui: VideoQualitiesUiList
    ): PlayerState {
        (prevState.ui as LceState.Content).data.qualities = ui
        val state = PlayerState(
            ui = LceState.Content(
                PlayerResult(
                    ui,
                    prevState.ui.data.videos
                ),
            ),
            isNewVideo = true,
            page = prevState.page
        )
        return state
    }

    private fun updateLoading(prevState: PlayerState) =
        prevState.copy(ui = LceState.Loading, isNewVideo = false)

    private fun updateError(prevState: PlayerState, throwable: Throwable) =
        prevState.copy(ui = LceState.Error(throwable), isNewVideo = false)

    private fun updateDataLoaded(prevState: PlayerState, ui: PlayerResult) =
        prevState.copy(ui = LceState.Content(ui), page = prevState.page + 1, isNewVideo = false)
}