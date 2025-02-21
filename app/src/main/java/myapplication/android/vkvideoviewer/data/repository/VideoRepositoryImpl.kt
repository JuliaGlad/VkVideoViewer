package myapplication.android.vkvideoviewer.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.vkvideoviewer.data.mapper.toDto
import myapplication.android.vkvideoviewer.data.repository.dto.VideoDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.VideoQualitiesDtoList
import myapplication.android.vkvideoviewer.data.source.VideosLocalSource
import myapplication.android.vkvideoviewer.data.source.VideosRemoteSource
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val remoteSource: VideosRemoteSource,
    private val localSource: VideosLocalSource
): VideoRepository {
    override suspend fun getVideos(page: Int): VideoDtoList {
        val local = localSource.getVideos(page)
        val result = if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getVideos(page)
            }
            localSource.deleteAll()
            localSource.insertVideos(page, remote)
            remote
        }.toDto()
        for (i in result.items){
            Log.i("Items videos repository id", i.id.toString())
        }
        return result
    }

    override suspend fun getVideosByQuery(query: String, page: Int): VideoDtoList {
        val local = localSource.getVideosByQuery(query, page)
        return if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getVideos(page)
            }
            localSource.deleteSeveralVideos(remote.items.size)
            localSource.insertVideos(page, remote)
            remote
        }.toDto()
    }

    override suspend fun getVideoQuality(page: Int, id: Int): VideoQualitiesDtoList {
        val local = localSource.getVideo(id)
        return if (local != null) local.videos
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getVideosById(id)
            }
            localSource.insertVideo(page, remote)
            remote.videos
        }.toDto()
    }
}