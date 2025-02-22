package myapplication.android.vkvideoviewer.data.api

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("videos?safesearch=true")
    suspend fun getVideos(@Query("page") page: Int): VideoList

    @GET("videos?safesearch=true")
    suspend fun getVideoById(@Query("id") id: String): VideoList

    @GET("?safesearch=true")
    suspend fun getImagesByCategory(@Query("category") category: String, @Query("page") page: Int): ImageList

    @GET("?safesearch=true")
    suspend fun getImages(@Query("page")page: Int): ImageList

    @GET("?safesearch=true")
    suspend fun getImageById(@Query("id")id: Int): Image
}