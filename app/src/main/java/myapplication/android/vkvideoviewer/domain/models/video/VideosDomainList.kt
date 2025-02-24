package myapplication.android.vkvideoviewer.domain.models.video

class VideosDomainList(
    val items: List<VideoDomainModel>
)

class VideoDomainModel(
    val id: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val thumbnail: String,
    val url: String
)