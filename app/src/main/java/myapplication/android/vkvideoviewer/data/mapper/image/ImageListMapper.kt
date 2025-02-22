package myapplication.android.vkvideoviewer.data.mapper.image

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.database.entities.ImageEntity

fun ImageEntity.toImage() =
    Image(
        imageId = imageId,
        title = title,
        largeImageUrl = largeImageUrl,
        previewUrl = previewUrl,
        views = views,
        downloads = downloads
    )