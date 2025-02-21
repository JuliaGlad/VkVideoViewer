package myapplication.android.vkvideoviewer.data.mapper

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import myapplication.android.vkvideoviewer.data.repository.dto.VideoDto
import myapplication.android.vkvideoviewer.data.repository.dto.VideoDtoList
import java.util.stream.Collectors


fun VideoList.toDto() =
    VideoDtoList(
        items.stream()
            .map { it.toDto() }
            .collect(Collectors.toList())
    )

fun Video.toDto() =
    VideoDto(
        id = videoId,
        title = title,
        duration = duration,
        views = views,
        downloads = downloads,
        videos = videos.toDto()
    )