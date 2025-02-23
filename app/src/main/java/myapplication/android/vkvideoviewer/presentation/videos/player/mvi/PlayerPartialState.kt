package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList
import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState
import myapplication.android.vkvideoviewer.presentation.videos.player.model.PlayerResult
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualitiesUiList

sealed interface PlayerPartialState: MviPartialState{

    data object Loading: PlayerPartialState

    data class Error(val throwable: Throwable): PlayerPartialState

    data class DataLoaded(val data: PlayerResult): PlayerPartialState

    data class VideoQualitiesLoaded(val qualities: VideoQualitiesUiList): PlayerPartialState

    data class NewVideosLoaded(val videos: VideoUiList): PlayerPartialState
}