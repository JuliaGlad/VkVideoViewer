package myapplication.android.vkvideoviewer.presentation.images.images.di

import dagger.Binds
import dagger.Module
import myapplication.android.vkvideoviewer.data.repository.ImageRepository
import myapplication.android.vkvideoviewer.data.repository.ImageRepositoryImpl
import myapplication.android.vkvideoviewer.data.source.image.ImageLocalSource
import myapplication.android.vkvideoviewer.data.source.image.ImageLocalSourceImpl
import myapplication.android.vkvideoviewer.data.source.image.ImageRemoteSource
import myapplication.android.vkvideoviewer.data.source.image.ImageRemoteSourceImpl

@Module
interface ImageFragmentModule {
    @Binds
    @ImageFragmentScope
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository

    @Binds
    @ImageFragmentScope
    fun bindImageRemoteSource(imageRemoteSourceImpl: ImageRemoteSourceImpl): ImageRemoteSource

    @Binds
    @ImageFragmentScope
    fun bindImageLocalSource(imageLocalSourceImpl: ImageLocalSourceImpl): ImageLocalSource
}