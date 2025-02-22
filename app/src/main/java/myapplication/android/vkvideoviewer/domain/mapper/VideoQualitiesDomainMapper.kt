package myapplication.android.vkvideoviewer.domain.mapper

import myapplication.android.vkvideoviewer.data.repository.dto.VideoQualitiesDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.VideoQualityDto
import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainList
import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainModel

fun VideoQualitiesDtoList.toDomain() =
    VideoQualityDomainList(
        large = large.toDomain(),
        medium = medium.toDomain(),
        small = small.toDomain(),
        tiny = tiny.toDomain()
    )

fun VideoQualityDto.toDomain() =
    VideoQualityDomainModel(
        url = url,
        size = size,
        thumbnail = thumbnail
    )