package myapplication.android.vkvideoviewer.data.mapper.video

import myapplication.android.vkvideoviewer.data.api.models.VideoQualitiesList
import myapplication.android.vkvideoviewer.data.api.models.VideoQuality
import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoQualitiesDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoQualityDto

fun VideoQualitiesList.toDto() =
    VideoQualitiesDtoList(
        medium = medium.toDto(),
        small = small.toDto(),
        tiny = tiny.toDto()
    )

fun VideoQuality.toDto() =
    VideoQualityDto(
        url = url,
        size = size,
        width = width,
        height = height,
        thumbnail = thumbnail
    )