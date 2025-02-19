package myapplication.android.vkvideoviewer.domain.models

class VideosDomainList(
    val items: List<VideoDomainModel>
)

class VideoDomainModel(
    val id: Int,
    val title: String,
    val duration: Int,
    val thumbnail: String,
)