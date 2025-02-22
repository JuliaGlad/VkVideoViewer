package myapplication.android.vkvideoviewer.presentation.videos.video.di

import dagger.Module
import dagger.Provides
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.presentation.videos.video.mvi.VideoLocalDI

@Module
class VideoFragmentLocalDIModule {
    @Provides
    @VideoFragmentScope
    fun provideVideoLocalDI(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoLocalDI = VideoLocalDI(videoRepositoryImpl)
}