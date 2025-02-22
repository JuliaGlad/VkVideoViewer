package myapplication.android.vkvideoviewer.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ImageList(
    @SerialName("hits") val items: List<Image>
)
@Serializable
class Image(
    @SerialName("id") val imageId: Int,
    @SerialName("tags") val title: String,
    @SerialName("largeImageURL") val largeImageUrl: String,
    @SerialName("previewURL") val previewUrl: String,
    val views: Int,
    val downloads: Int
)