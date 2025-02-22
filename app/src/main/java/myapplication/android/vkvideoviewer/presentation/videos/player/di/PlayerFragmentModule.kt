package myapplication.android.vkvideoviewer.presentation.videos.player.di

import dagger.Binds
import dagger.Module
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.data.source.video.VideosLocalSource
import myapplication.android.vkvideoviewer.data.source.video.VideosLocalSourceImpl
import myapplication.android.vkvideoviewer.data.source.video.VideosRemoteSource
import myapplication.android.vkvideoviewer.data.source.video.VideosRemoteSourceImpl

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