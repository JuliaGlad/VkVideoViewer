package myapplication.android.vkvideoviewer.data.repository.dto

class VideoDtoList(
    val items: List<VideoDto>
)

class VideoDto(
    val id: Int,
    val title: String,
    val duration: Int,
    val views: Int,
    val downloads: Int,
    val videos: VideoQualitiesDtoList
)