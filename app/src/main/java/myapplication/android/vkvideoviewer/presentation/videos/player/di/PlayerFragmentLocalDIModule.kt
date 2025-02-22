package myapplication.android.vkvideoviewer.presentation.videos.player.di

import dagger.Module
import dagger.Provides
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.presentation.videos.player.mvi.PlayerLocalDI

@Module
class PlayerFragmentLocalDIModule {

    @Provides
    @PlayerFragmentScope
    fun providePlayerLocalDi(
        videoRepository: VideoRepository
    ): PlayerLocalDI = PlayerLocalDI(videoRepository)

}