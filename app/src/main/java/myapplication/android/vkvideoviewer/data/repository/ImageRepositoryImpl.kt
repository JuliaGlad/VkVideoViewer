package myapplication.android.vkvideoviewer.data.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import myapplication.android.vkvideoviewer.data.mapper.image.toDto
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDto
import myapplication.android.vkvideoviewer.data.repository.dto.image.ImageDtoList
import myapplication.android.vkvideoviewer.data.source.image.ImageLocalSource
import myapplication.android.vkvideoviewer.data.source.image.ImageRemoteSource
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteSource: ImageRemoteSource,
    private val localSource: ImageLocalSource
): ImageRepository {
    override suspend fun getImageByCategory(category: String, page: Int): ImageDtoList {
        Log.i("Category", category.toString())
        val local = localSource.getImagesByCategory(category, page)
        return if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getImagesByCategory(category, page)
            }
            localSource.deleteSeveralImages(remote.items.size)
            localSource.insertImages(page, remote)
            remote
        }.toDto()
    }

    override suspend fun getImages(page: Int): ImageDtoList {
        val local = localSource.getImages(page)
        val result = if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getImages(page)
            }
            localSource.deleteAll()
            localSource.insertImages(page, remote)
            remote
        }.toDto()
        return result
    }

    override suspend fun getImageById(id: Int, page: Int): ImageDto {
        val local = localSource.getImageById(id)
        return if (local != null) local
        else {
            val remote = withContext(Dispatchers.IO){
                remoteSource.getImageById(id)
            }
            localSource.insertImage(page, remote)
            remote
        }.toDto()
    }
}