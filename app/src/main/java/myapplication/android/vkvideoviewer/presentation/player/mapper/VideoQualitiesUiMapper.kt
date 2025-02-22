package myapplication.android.vkvideoviewer.presentation.player.mapper

import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainList
import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainModel
import myapplication.android.vkvideoviewer.presentation.player.model.VideoQualitiesUiList
import myapplication.android.vkvideoviewer.presentation.player.model.VideoQualityUiModel

fun VideoQualityDomainList.toUi() =
    VideoQualitiesUiList(
        large = large.toUi(),
        medium = medium.toUi(),
        small = small.toUi(),
        tiny = tiny.toUi()
    )

fun VideoQualityDomainModel.toUi() =
    VideoQualityUiModel(
        url = url,
        size = size
    )