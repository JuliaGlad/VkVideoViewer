package myapplication.android.vkvideoviewer.domain.models

class VideoQualityDomainList(
    val large: VideoQualityDomainModel,
    val medium: VideoQualityDomainModel,
    val small: VideoQualityDomainModel,
    val tiny: VideoQualityDomainModel
)

class VideoQualityDomainModel(
    val url: String,
    val thumbnail: String
)