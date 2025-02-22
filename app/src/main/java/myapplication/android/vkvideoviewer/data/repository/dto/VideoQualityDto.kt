package myapplication.android.vkvideoviewer.data.repository.dto

class VideoQualitiesDtoList(
    val large: VideoQualityDto,
    val medium: VideoQualityDto,
    val small: VideoQualityDto,
    val tiny: VideoQualityDto
)

class VideoQualityDto(
    val url: String,
    val size: Int,
    val width: Int,
    val height: Int,
    val thumbnail: String
)