package myapplication.android.vkvideoviewer.data.mapper

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoQualitiesList
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity

fun VideoEntity.toVideo() =
    Video(
        videoId = videoId,
        title = title,
        duration = duration,
        views = views,
        downloads = downloads,
        videos = VideoQualitiesList(
            large, medium, small, tiny
        )
    )