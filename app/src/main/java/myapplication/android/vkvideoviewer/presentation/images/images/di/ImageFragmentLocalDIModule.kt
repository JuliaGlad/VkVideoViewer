package myapplication.android.vkvideoviewer.presentation.images.images.di

import dagger.Module
import dagger.Provides
import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.presentation.images.images.mvi.ImagesLocalDI

@Module
class ImageFragmentLocalDIModule {

    @Provides
    @ImageFragmentScope
    fun provideImageLocalDI(
        imageRepository: ImageRepository
    ): ImagesLocalDI = ImagesLocalDI(imageRepository)

}