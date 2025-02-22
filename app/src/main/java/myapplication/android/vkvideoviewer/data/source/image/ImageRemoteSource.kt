package myapplication.android.vkvideoviewer.data.source.image

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList

interface ImageRemoteSource {

    suspend fun getImages(page: Int): ImageList

    suspend fun getImagesByCategory(category: String, page: Int): ImageList

    suspend fun getImageById(id: Int): Image

}