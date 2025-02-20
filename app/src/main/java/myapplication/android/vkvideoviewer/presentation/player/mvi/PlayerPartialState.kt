package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.presentation.model.VideoUiList
import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerResult

sealed interface PlayerPartialState: MviPartialState{

    data object Loading: PlayerPartialState

    data class Error(val throwable: Throwable): PlayerPartialState

    data class DataLoaded(val data: PlayerResult): PlayerPartialState

    data class NewVideosLoaded(val videos: VideoUiList): PlayerPartialState
}