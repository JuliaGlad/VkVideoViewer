package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviEffect

sealed interface VideoEffect: MviEffect {

    data class OpenVideoActivity(
        val videoId: Int,
        val page: Int,
        val title: String,
        val thumbnail: String,
        val views: Int,
        val downloads: Int
    ): VideoEffect

    data class OpenDownloadingMenu(val url: String): VideoEffect
}