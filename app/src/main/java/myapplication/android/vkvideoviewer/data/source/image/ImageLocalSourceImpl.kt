package myapplication.android.vkvideoviewer.data.source.image

import myapplication.android.vkvideoviewer.data.api.models.Image
import myapplication.android.vkvideoviewer.data.api.models.ImageList
import myapplication.android.vkvideoviewer.data.database.entities.ImageEntity
import myapplication.android.vkvideoviewer.data.database.provider.ImageProvider
import myapplication.android.vkvideoviewer.data.mapper.image.toImage
import java.util.stream.Collectors
import javax.inject.Inject

class ImageLocalSourceImpl @Inject constructor(): ImageLocalSource {
    override fun getImages(page: Int): ImageList? {
        val data = ImageProvider().getImages(page)
        return if (data?.isNotEmpty() == true) {
            ImageList(
                data.stream()
                    .map { it.toImage() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun getImageById(id: Int): Image? {
        val image: ImageEntity? = ImageProvider().getImage(id)
        return if (image != null) return image.toImage()
        else null
    }

    override fun getImagesByCategory(category: String, page: Int): ImageList? {
        val data = ImageProvider().getImagesByCategory(category, page)
        return if (data?.isNotEmpty() == true) {
            ImageList(
                data.stream()
                    .map { it.toImage() }
                    .collect(Collectors.toList())
            )
        } else null
    }

    override fun insertImage(page: Int, image: Image) {
        ImageProvider().insertImage(page, image)
    }

    override fun insertImages(page: Int, images: ImageList) {
        ImageProvider().insertAll(page, images)
    }

    override fun deleteSeveralImages(size: Int) {
        ImageProvider().deleteSeveral(size)
    }

    override fun deleteAll() {
        ImageProvider().deleteAll()
    }

}