package myapplication.android.vkvideoviewer.data.source.image

import myapplication.android.vkvideoviewer.data.api.VideoApi
import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import javax.inject.Inject

class ImageRemoteSourceImpl @Inject constructor(
    private val videoApi: VideoApi
) : ImageRemoteSource {
    override suspend fun getImages(page: Int): ImageList =
        videoApi.getImages(page)

    override suspend fun getImagesByCategory(category: String, page: Int): ImageList =
        videoApi.getImagesByCategory(category, page)

    override suspend fun getImageById(id: Int): Image =
        videoApi.getImageById(id)
}