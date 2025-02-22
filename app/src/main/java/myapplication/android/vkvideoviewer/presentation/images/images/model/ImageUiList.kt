package myapplication.android.vkvideoviewer.presentation.images.images.model

class ImageUiList(
    val items: List<ImageUiModel>
)

class ImageUiModel(
    val imageId: Int,
    val title: String,
    val previewUrl: String,
    val largeImageUrl: String,
    val views: Int,
    val downloads: Int
)