package myapplication.android.vkvideoviewer.domain.models.video

class VideoQualityDomainList(
    val medium: VideoQualityDomainModel,
    val small: VideoQualityDomainModel,
    val tiny: VideoQualityDomainModel
)

class VideoQualityDomainModel(
    val url: String,
    val size: Int,
    val thumbnail: String
)