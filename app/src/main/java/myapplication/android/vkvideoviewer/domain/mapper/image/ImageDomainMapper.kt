package myapplication.android.vkvideoviewer.domain.mapper.image

import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDto
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDtoList
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainModel
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainList
import java.util.stream.Collectors

fun ImageDtoList.toDomain(): ImageDomainList =
    ImageDomainList(
        items.stream()
            .map { it.toDomain() }
            .collect(Collectors.toList())
    )

fun ImageDto.toDomain(): ImageDomainModel =
    ImageDomainModel(
        imageId = imageId,
        title = title,
        largeImageUrl = largeImageUrl,
        previewUrl = previewUrl,
        views = views,
        downloads = downloads
    )
