package myapplication.android.vkvideoviewer.data.repository.dto

class VideoDtoList(
    val items: List<VideoDto>
)

class VideoDto(
    val id: Int,
    val title: String,
    val duration: Int,
    val videos: VideoQualitiesDtoList
)