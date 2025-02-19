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

fun VideoDtoList.toDomain(query: String) =
    VideosDomainList(
        items.stream()
            .filter { it.title.lowercase().contains(query.lowercase()) }
            .map { it.toDomain() }
            .collect(Collectors.toList())
    )

fun VideoDto.toDomain() =
    VideoDomainModel(
        id = id,
        title = title,
        duration = duration,
        views = views,
        downloads = downloads,
        thumbnail = videos.medium.thumbnail,
        url = videos.medium.url
    )