package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.domain.usecase.image.GetImagesByCategoryUseCase
import myapplication.android.vkvideoviewer.domain.usecase.image.GetImagesUseCase
import javax.inject.Inject

class ImagesLocalDI @Inject constructor(
    private val imageRepository: ImageRepository
) {
    private val getImagesUseCase by lazy { GetImagesUseCase(imageRepository) }

    private val getImagesByCategoryUseCase by lazy { GetImagesByCategoryUseCase(imageRepository) }

    val actor by lazy { ImagesActor(getImagesUseCase, getImagesByCategoryUseCase) }

    val reducer by lazy { ImagesReducer() }
}