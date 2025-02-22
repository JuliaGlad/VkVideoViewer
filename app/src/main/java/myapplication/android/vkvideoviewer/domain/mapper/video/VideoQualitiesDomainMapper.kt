package myapplication.android.vkvideoviewer.domain.mapper.video

import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoQualitiesDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoQualityDto
import myapplication.android.vkvideoviewer.domain.models.video.VideoQualityDomainList
import myapplication.android.vkvideoviewer.domain.models.video.VideoQualityDomainModel

fun VideoQualitiesDtoList.toDomain() =
    VideoQualityDomainList(
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