package myapplication.android.vkvideoviewer.data.api

import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("videos?safesearch=true")
    suspend fun getVideos(@Query("page") page: Int): VideoList

    @GET("videos?safesearch=true")
    suspend fun getVideoById(@Query("id") id: String): VideoList
}