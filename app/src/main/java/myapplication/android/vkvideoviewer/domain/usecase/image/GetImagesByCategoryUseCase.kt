package myapplication.android.vkvideoviewer.domain.usecase.image

import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.domain.mapper.image.toDomain
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainList
import javax.inject.Inject

class GetImagesByCategoryUseCase @Inject constructor(
    private val imagesRepository: ImageRepository
) {
    suspend fun invoke(category: String, page: Int): ImageDomainList =
        imagesRepository.getImageByCategory(category, page).toDomain()
}