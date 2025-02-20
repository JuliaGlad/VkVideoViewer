package myapplication.android.vkvideoviewer.presentation.player.recycler_view

import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

data class VideoHorizontalItemModel(
    val id: Int,
    val videoId: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val thumbnail: String,
    val itemClickListener: ClickListener,
    val actionClickListener: ClickListener
)