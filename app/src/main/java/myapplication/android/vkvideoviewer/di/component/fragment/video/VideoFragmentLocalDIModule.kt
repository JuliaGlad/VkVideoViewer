package myapplication.android.vkvideoviewer.di.component.fragment.video

import dagger.Module
import dagger.Provides
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.presentation.video.mvi.VideoLocalDI

@Module
class VideoFragmentLocalDIModule {
    @Provides
    @VideoFragmentScope
    fun provideVideoLocalDI(
        videoRepositoryImpl: VideoRepositoryImpl
    ): VideoLocalDI = VideoLocalDI(videoRepositoryImpl)
}