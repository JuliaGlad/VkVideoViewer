package myapplication.android.vkvideoviewer.data.repository.dto.image

class ImageDtoList(
    val items: List<ImageDto>
)

class ImageDto(
    val imageId: Int,
    val title: String,
    val largeImageUrl: String,
    val previewUrl: String,
    val views: Int,
    val downloads: Int
)