package myapplication.android.vkvideoviewer.di.component.fragment.video

import dagger.Binds
import dagger.Module
import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.data.repository.VideoRepositoryImpl
import myapplication.android.vkvideoviewer.data.source.VideosLocalSource
import myapplication.android.vkvideoviewer.data.source.VideosLocalSourceImpl
import myapplication.android.vkvideoviewer.data.source.VideosRemoteSource
import myapplication.android.vkvideoviewer.data.source.VideosRemoteSourceImpl

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