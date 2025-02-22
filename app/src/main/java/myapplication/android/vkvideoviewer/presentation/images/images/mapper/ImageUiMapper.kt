package myapplication.android.vkvideoviewer.presentation.images.images.mapper

import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainList
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainModel
import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiModel
import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiList
import java.util.stream.Collectors

fun ImageDomainList.toUi(): ImageUiList =
    ImageUiList(
        items.stream()
            .map { it.toUi() }
            .collect(Collectors.toList())
    )

fun ImageDomainModel.toUi(): ImageUiModel =
    ImageUiModel(
        imageId = imageId,
        title = title,
        views = views,
        previewUrl = previewUrl,
        largeImageUrl = largeImageUrl,
        downloads = downloads
    )
