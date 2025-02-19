package myapplication.android.vkvideoviewer.data.source

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import myapplication.android.vkvideoviewer.data.database.provider.VideoProvider
import myapplication.android.vkvideoviewer.data.mapper.toVideo
import java.util.stream.Collectors

class VideosLocalSourceImpl: VideosLocalSource {
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

    override fun getVideo(id: Int): Video? =
        VideoProvider().getVideo(id)?.toVideo()

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