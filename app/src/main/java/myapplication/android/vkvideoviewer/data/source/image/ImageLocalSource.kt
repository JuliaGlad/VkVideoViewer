package myapplication.android.vkvideoviewer.data.source.image

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import myapplication.android.vkvideoviewer.data.api.models.Video
import myapplication.android.vkvideoviewer.data.api.models.VideoList

interface ImageLocalSource {

    fun getImages(page: Int): ImageList?

    fun getImageById(id: Int): Image?

    fun getImagesByCategory(category: String, page: Int): ImageList?

    fun insertImage(page: Int, image: Image)

    fun insertImages(page: Int, images: ImageList)

    fun deleteSeveralImages(size: Int)

    fun deleteAll()
}