package myapplication.android.vkvideoviewer.presentation.videos.model

data class VideoUiList(
    val items: List<VideoUiModel>,
    val savedItems: List<VideoUiModel>,
    val isSearch: Boolean = false
)

data class VideoUiModel(
    val id: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val url: String,
    val thumbnail: String,
)