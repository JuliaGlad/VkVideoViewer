package myapplication.android.vkvideoviewer.presentation.videos.video.mapper

import myapplication.android.vkvideoviewer.domain.models.video.VideoDomainModel
import myapplication.android.vkvideoviewer.domain.models.video.VideosDomainList
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiModel
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList
import java.util.stream.Collectors

fun VideosDomainList.toUi(): VideoUiList {
    val mappedItems = items.stream()
        .map { it.toUi() }
        .collect(Collectors.toList())
    return VideoUiList(
        items = mappedItems,
        savedItems = mappedItems,
        isSearch = false
    )
}

fun VideoDomainModel.toUi() =
    VideoUiModel(
        id = id,
        title = title,
        duration = duration,
        views = views,
        downloads = downloads,
        url = url,
        thumbnail = thumbnail
    )