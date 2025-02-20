package myapplication.android.vkvideoviewer.data.mapper

import myapplication.android.vkvideoviewer.data.api.models.VideoQualitiesList
import myapplication.android.vkvideoviewer.data.api.models.VideoQuality
import myapplication.android.vkvideoviewer.data.repository.dto.VideoQualitiesDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.VideoQualityDto

fun VideoQualitiesList.toDto() =
    VideoQualitiesDtoList(
        large = large.toDto(),
        medium = medium.toDto(),
        small = small.toDto(),
        tiny = tiny.toDto()
    )

fun VideoQuality.toDto() =
    VideoQualityDto(
        url = url,
        width = width,
        height = height,
        thumbnail = thumbnail
    )