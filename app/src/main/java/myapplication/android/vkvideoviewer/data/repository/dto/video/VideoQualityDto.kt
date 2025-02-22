package myapplication.android.vkvideoviewer.data.repository.dto.video

class VideoQualitiesDtoList(
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