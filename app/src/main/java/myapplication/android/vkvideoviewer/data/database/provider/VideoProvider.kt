package myapplication.android.vkvideoviewer.data.database.provider

import myapplication.android.vkvideoviewer.app.App.Companion.app
import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity

class VideoProvider {

    fun getVideos(page: Int): MutableList<VideoEntity>? {
        val data = app.database.videoDao().getVideos()
        var result: MutableList<VideoEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            for (i in data) {
                if (page == i.page) {
                    result?.add(i)
                }
            }
        }
        return result
    }

    fun getVideosByQuery(query: String, page: Int): List<VideoEntity>? {
        val data = app.database.videoDao().getVideos()
        var result: MutableList<VideoEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            for (i in data) {
                with(i) {
                    if (page == i.page && title.lowercase().contains(query.lowercase())) {
                        result?.add(i)
                    }
                }
            }
        }
        return result
    }

    fun getVideo(id: Int): VideoEntity? {
        val data = app.database.videoDao().getVideos()
        if (data.isEmpty()) return null
        else {
            for (i in data) {
                if (i.videoId == id) {
                    return i
                }
            }
        }
        return null
    }

    fun insertAll(page: Int, videoList: VideoList) {
        val entities = mutableListOf<VideoEntity>()
        for (i in videoList.items) {
            with(i) {
                entities.add(
                    VideoEntity(
                        videoId = id,
                        page = page,
                        title = title,
                        duration = duration,
                        views = views,
                        downloads = downloads,
                        large = videos.large,
                        medium = videos.medium,
                        small = videos.small,
                        tiny = videos.tiny
                    )
                )
            }
        }
        app.database.videoDao().insertAll(entities)
    }

    fun insertVideo(page: Int, video: Video) {
        with(video) {
            app.database.videoDao().insertVideo(
                VideoEntity(
                    videoId = id,
                    page = page,
                    title = title,
                    duration = duration,
                    views = views,
                    downloads = downloads,
                    large = videos.large,
                    medium = videos.medium,
                    small = videos.small,
                    tiny = videos.tiny
                )
            )
        }
    }


    fun deleteAll() {
        app.database.videoDao().deleteAll()
    }

    fun deleteSeveral(size: Int) {
        val dao = app.database.videoDao()
        val data = dao.getVideos()
        if (data.size >= 20) {
            for (i in data) {
                if (data.indexOf(i) >= size) break
                else dao.deleteVideo(i)
            }
        }
    }

}