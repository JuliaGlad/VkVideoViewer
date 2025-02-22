package myapplication.android.vkvideoviewer.data.api.models

import kotlinx.serialization.Serializable

@Serializable
class VideoQualitiesList(
    val large: VideoQuality,
    val medium: VideoQuality,
    val small: VideoQuality,
    val tiny: VideoQuality
)

@Serializable
class VideoQuality(
    val url: String,
    val size: Int,
    val width: Int,
    val height: Int,
    val thumbnail: String
)