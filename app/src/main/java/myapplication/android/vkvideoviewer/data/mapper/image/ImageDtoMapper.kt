package myapplication.android.vkvideoviewer.data.mapper.image

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDto
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDtoList
import java.util.stream.Collectors

fun ImageList.toDto(): ImageDtoList =
    ImageDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )
fun Image.toDto() : ImageDto =
    ImageDto(
        imageId = imageId,
        title = title,
        largeImageUrl = largeImageUrl,
        previewUrl = previewUrl,
        views = views,
        downloads = downloads
    )
