package myapplication.android.vkvideoviewer.domain.usecase.image

import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.domain.mapper.image.toDomain
import myapplication.android.vkvideoviewer.domain.models.image.ImageDomainModel
import javax.inject.Inject

class GetImageByIdUseCase@Inject constructor(
    private val imagesRepository: ImageRepository
) {
    suspend fun invoke(id: Int, page: Int): ImageDomainModel =
        imagesRepository.getImageById(id, page).toDomain()
}