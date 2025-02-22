package myapplication.android.vkvideoviewer.presentation.videos.player.mapper

import myapplication.android.vkvideoviewer.domain.models.video.VideoQualityDomainList
import myapplication.android.vkvideoviewer.domain.models.video.VideoQualityDomainModel
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualitiesUiList
import myapplication.android.vkvideoviewer.presentation.videos.player.model.VideoQualityUiModel

fun VideoQualityDomainList.toUi() =
    VideoQualitiesUiList(
        medium = medium.toUi(),
        small = small.toUi(),
        tiny = tiny.toUi()
    )

fun VideoQualityDomainModel.toUi() =
    VideoQualityUiModel(
        url = url,
        size = size
    )