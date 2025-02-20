package myapplication.android.vkvideoviewer.data.source

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList

interface VideosLocalSource {

    fun getVideos(page: Int): VideoList?

    fun getVideo(id: Int): Video?

    fun getVideosByQuery(query: String, page: Int): VideoList?

    fun insertVideo(page: Int, video: Video)

    fun insertVideos(page: Int, videos: VideoList)

    fun deleteSeveralVideos(size: Int)

    fun deleteAll()
}