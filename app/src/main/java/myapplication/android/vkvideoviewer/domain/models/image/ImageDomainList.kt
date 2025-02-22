package myapplication.android.vkvideoviewer.domain.models.image

class ImageDomainList(
    val items: List<ImageDomainModel>
)

class ImageDomainModel(
    val imageId: Int,
    val title: String,
    val previewUrl: String,
    val largeImageUrl: String,
    val views: Int,
    val downloads: Int
)