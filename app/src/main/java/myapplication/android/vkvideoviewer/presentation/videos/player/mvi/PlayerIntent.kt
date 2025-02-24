package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviIntent

sealed interface PlayerIntent: MviIntent {

    class GetVideos(val videoPage: Int, val videoId: Int): PlayerIntent

    class GetVideoQuality(val videoPage: Int, val videoId: Int): PlayerIntent

    data object GetNewVideos: PlayerIntent
}