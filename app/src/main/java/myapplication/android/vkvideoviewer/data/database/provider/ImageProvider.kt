package myapplication.android.vkvideoviewer.data.database.provider

import myapplication.android.vkvideoviewer.App.Companion.app
import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import myapplication.android.vkvideoviewer.data.database.entities.ImageEntity

class ImageProvider {
    fun getImages(page: Int): List<ImageEntity>? {
        val data = app.database.imageDao().getImages()
        var result: MutableList<ImageEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            for (i in data) {
                if (page == i.page) {
                    result?.add(i)
                }
            }
        }
        return result
    }

    fun getImagesByCategory(category: String, page: Int): List<ImageEntity>? {
        val data = app.database.imageDao().getImages()
        var result: MutableList<ImageEntity>? = mutableListOf()
        if (data.isEmpty()) result = null
        else {
            for (i in data) {
                with(i) {
                    if (page == i.page && title.lowercase().contains(category.lowercase())) {
                        result?.add(i)
                    }
                }
            }
        }
        return result
    }

    fun getImage(id: Int): ImageEntity? {
        val data = app.database.imageDao().getImages()
        if (data.isEmpty()) return null
        else {
            for (i in data) {
                if (i.imageId == id) {
                    return i
                }
            }
        }
        return null
    }

    fun insertAll(page: Int, imageList: ImageList) {
        val entities = mutableListOf<ImageEntity>()
        for (i in imageList.items) {
            with(i) {
                entities.add(
                    ImageEntity(
                        imageId = imageId,
                        page = page,
                        title = title,
                        previewUrl = previewUrl,
                        largeImageUrl = largeImageUrl,
                        views = views,
                        downloads = downloads
                    )
                )
            }
        }
        app.database.imageDao().insertAll(entities)
    }

    fun insertImage(page: Int, image: Image) {
        with(image) {
            app.database.imageDao().insertImage(
                ImageEntity(
                    imageId = imageId,
                    page = page,
                    title = title,
                    previewUrl = previewUrl,
                    largeImageUrl = largeImageUrl,
                    views = views,
                    downloads = downloads
                )
            )
        }
    }


    fun deleteAll() {
        app.database.imageDao().deleteAll()
    }

    fun deleteSeveral(size: Int) {
        val dao = app.database.imageDao()
        val data = dao.getImages()
        if (data.size >= 20) {
            for (i in data) {
                if (data.indexOf(i) >= size) break
                else dao.deleteImage(i)
            }
        }
    }
}