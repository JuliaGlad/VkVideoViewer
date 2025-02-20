package myapplication.android.vkvideoviewer.data.source

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList

interface VideosRemoteSource {

    suspend fun getVideos(page: Int): VideoList

    suspend fun getVideosById(id: Int): Video
}