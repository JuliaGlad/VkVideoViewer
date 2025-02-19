package myapplication.android.vkvideoviewer.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class VideoList(
    @SerialName("hits") val items: List<Video>
)

@Serializable
class Video(
    val id: Int,
    @SerialName("tags") val title: String,
    val duration: Int,
    val videos: VideoQualitiesList
)
