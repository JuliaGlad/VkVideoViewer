package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviIntent

sealed interface PlayerIntent: MviIntent {

    data class GetVideos(val videoPage: Int, val videoId: Int): PlayerIntent

    data object GetNewVideos: PlayerIntent
}