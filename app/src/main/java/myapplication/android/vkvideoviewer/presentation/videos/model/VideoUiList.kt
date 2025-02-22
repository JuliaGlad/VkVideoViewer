package myapplication.android.vkvideoviewer.presentation.videos.model

class VideoUiList(
    val items: List<VideoUiModel>
)

class VideoUiModel(
    val id: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val url: String,
    val thumbnail: String,
)