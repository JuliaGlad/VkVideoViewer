package myapplication.android.vkvideoviewer.domain.mapper

import myapplication.android.vkvideoviewer.data.repository.dto.VideoDto
import myapplication.android.vkvideoviewer.data.repository.dto.VideoDtoList
import myapplication.android.vkvideoviewer.domain.models.VideoDomainModel
import myapplication.android.vkvideoviewer.domain.models.VideosDomainList
import java.util.stream.Collectors

fun VideoDtoList.toDomain() =
    VideosDomainList(
        items.stream()
            .map { it.toDomain() }
            .collect(Collectors.toList())
    )

fun VideoDto.toDomain() =
    VideoDomainModel(
        id = id,
        title = title,
        duration = duration,
        thumbnail = videos.medium.thumbnail
    )