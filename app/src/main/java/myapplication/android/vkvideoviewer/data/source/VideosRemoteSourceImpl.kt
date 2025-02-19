package myapplication.android.vkvideoviewer.data.source

import myapplication.android.vkvideoviewer.data.api.VideoApi
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import javax.inject.Inject

class VideosRemoteSourceImpl @Inject constructor(
    private val api: VideoApi
): VideosRemoteSource {
    override suspend fun getVideos(page: Int): VideoList =
        api.getVideos(page)

    override suspend fun getVideosByQuery(query: String, page: Int): VideoList =
        api.getVideosByQuery(query, page)
}