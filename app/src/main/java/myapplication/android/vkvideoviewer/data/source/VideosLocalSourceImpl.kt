package myapplication.android.vkvideoviewer.data.source

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import myapplication.android.vkvideoviewer.data.database.entities.VideoEntity
import myapplication.android.vkvideoviewer.data.database.provider.VideoProvider
import myapplication.android.vkvideoviewer.data.mapper.toVideo
import java.util.stream.Collectors
import javax.inject.Inject

class VideosLocalSourceImpl @Inject constructor(): VideosLocalSource {
    override fun getVideos(page: Int): VideoList?{
        val data = VideoProvider().getVideos(page)
        return if (data?.isNotEmpty() == true) {
           VideoList(
               data.stream()
                   .map { it.toVideo() }
                   .collect(Collectors.toList())
           )
        } else null
    }

    override fun getVideo(id: Int): Video? {
        val video : VideoEntity? = VideoProvider().getVideo(id)
        return if (video != null) return video.toVideo()
        else null
    }


    override fun getVideosByQuery(query: String, page: Int): VideoList? {
        val data = VideoProvider().getVideosByQuery(query, page)
        return if (data?.isNotEmpty() == true) {
            VideoList(
                data.stream()
                    .map { it.toVideo() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun insertVideo(page: Int, video: Video) {
        VideoProvider().insertVideo(page, video)
    }

    override fun insertVideos(page: Int, videos: VideoList) {
        VideoProvider().insertAll(page, videos)
    }

    override fun deleteSeveralVideos(size: Int) {
        VideoProvider().deleteSeveral(size)
    }

    override fun deleteAll() {
        VideoProvider().deleteAll()
    }

}