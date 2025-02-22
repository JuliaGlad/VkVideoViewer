package myapplication.android.vkvideoviewer.data.repository

import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoDtoList
import myapplication.android.vkvideoviewer.data.repository.dto.video.VideoQualitiesDtoList

interface VideoRepository {

    suspend fun getVideos(page: Int): VideoDtoList

    suspend fun getVideosByQuery(query: String, page: Int): VideoDtoList

    suspend fun getVideoQuality(page: Int, id: Int): VideoQualitiesDtoList
}