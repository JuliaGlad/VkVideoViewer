package myapplication.android.vkvideoviewer.presentation.player.di

import dagger.Binds
import dagger.Module
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.data.source.VideosLocalSource
import myapplication.android.vkvideoviewer.data.source.VideosLocalSourceImpl
import myapplication.android.vkvideoviewer.data.source.VideosRemoteSource
import myapplication.android.vkvideoviewer.data.source.VideosRemoteSourceImpl

@Module
interface PlayerFragmentModule {

    @PlayerFragmentScope
    @Binds
    fun bindVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository

    @PlayerFragmentScope
    @Binds
    fun bindVideoRemoteSource(videosRemoteSourceImpl: VideosRemoteSourceImpl): VideosRemoteSource

    @PlayerFragmentScope
    @Binds
    fun bindVideoLocalSource(videosLocalSourceImpl: VideosLocalSourceImpl): VideosLocalSource

}