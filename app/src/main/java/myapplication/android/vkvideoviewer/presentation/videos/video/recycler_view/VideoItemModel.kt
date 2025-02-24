package myapplication.android.vkvideoviewer.presentation.videos.video.recycler_view

import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

data class VideoItemModel(
    val id: Int,
    val videoId: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val thumbnail: String,
    val itemClickListener: ClickListener
)