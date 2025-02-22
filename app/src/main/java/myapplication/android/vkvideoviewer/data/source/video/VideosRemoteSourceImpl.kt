package myapplication.android.vkvideoviewer.data.source.video

import myapplication.android.vkvideoviewer.data.api.VideoApi
import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import javax.inject.Inject

class VideosRemoteSourceImpl @Inject constructor(
    private val api: VideoApi
): VideosRemoteSource {
    override suspend fun getVideos(page: Int): VideoList =
        api.getVideos(page)

    override suspend fun getVideosById(id: Int): Video =
        api.getVideoById(id.toString()).items[0]
}