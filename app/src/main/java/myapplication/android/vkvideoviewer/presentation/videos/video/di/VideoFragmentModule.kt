package myapplication.android.vkvideoviewer.presentation.videos.video.di

import dagger.Binds
import dagger.Module
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.data.source.video.VideosLocalSource
import myapplication.android.vkvideoviewer.data.source.video.VideosLocalSourceImpl
import myapplication.android.vkvideoviewer.data.source.video.VideosRemoteSource
import myapplication.android.vkvideoviewer.data.source.video.VideosRemoteSourceImpl

@Module
interface VideoFragmentModule {

    @Binds
    @VideoFragmentScope
    fun bindVideoRepository(videoRepositoryImpl: VideoRepositoryImpl): VideoRepository

    @Binds
    @VideoFragmentScope
    fun bindVideoRemoteSource(videosRemoteSourceImpl: VideosRemoteSourceImpl): VideosRemoteSource

    @Binds
    @VideoFragmentScope
    fun bindVideoLocalSource(videosLocalSourceImpl: VideosLocalSourceImpl): VideosLocalSource
}