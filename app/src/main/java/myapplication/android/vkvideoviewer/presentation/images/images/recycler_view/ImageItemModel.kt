package myapplication.android.vkvideoviewer.presentation.images.images.recycler_view

import myapplication.android.vkvideoviewer.presentation.listener.ClickListener

data class ImageItemModel(
    val id: Int,
    val imageId: Int,
    val title: String,
    val views: Int,
    val downloads: Int,
    val thumbnail: String,
    val itemClickListener: ClickListener
)