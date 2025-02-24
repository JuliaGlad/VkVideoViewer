package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList
import myapplication.android.vkvideoviewer.presentation.mvi.MviPartialState
import myapplication.android.vkvideoviewer.presentation.videos.player.model.PlayerResult
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualitiesUiList

sealed interface PlayerPartialState: MviPartialState{

    data object Loading: PlayerPartialState

    class Error(val throwable: Throwable): PlayerPartialState

    class DataLoaded(val data: PlayerResult): PlayerPartialState

    class VideoQualitiesLoaded(val qualities: VideoQualitiesUiList): PlayerPartialState

    class NewVideosLoaded(val videos: VideoUiList): PlayerPartialState
}