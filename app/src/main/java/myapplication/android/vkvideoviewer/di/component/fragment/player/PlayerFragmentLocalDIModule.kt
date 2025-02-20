package myapplication.android.vkvideoviewer.di.component.fragment.player

import dagger.Module
import dagger.Provides
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.presentation.player.mvi.PlayerLocalDI

@Module
class PlayerFragmentLocalDIModule {

    @Provides
    @PlayerFragmentScope
    fun providePlayerLocalDi(
        videoRepository: VideoRepository
    ): PlayerLocalDI = PlayerLocalDI(videoRepository)

}