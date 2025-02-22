package myapplication.android.vkvideoviewer.data.repository

import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDto
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDtoList

interface ImageRepository {
    suspend fun getImageByCategory(category: String, page: Int): ImageDtoList

    suspend fun getImages(page: Int): ImageDtoList

    suspend fun getImageById(id: Int, page: Int): ImageDto
}