package myapplication.android.vkvideoviewer.domain.usecase.image

import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.domain.mapper.image.toDomain
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainList
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imagesRepository: ImageRepository
) {
    suspend fun invoke(page: Int): ImageDomainList =
        imagesRepository.getImages(page).toDomain()
}