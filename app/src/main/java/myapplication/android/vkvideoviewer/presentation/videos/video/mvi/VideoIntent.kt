package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviIntent

sealed interface VideoIntent: MviIntent {

    data object Init: VideoIntent

    data object GetVideos: VideoIntent

    data class GetVideosByQuery(val query: String): VideoIntent
}