package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosByQueryUseCase
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosUseCase
import javax.inject.Inject

class VideoLocalDI @Inject constructor(
    private val videoRepository: VideoRepository
)  {

    private val getVideosUseCase by lazy { GetVideosUseCase(videoRepository) }

    private val getVideosByQueryUseCase by lazy { GetVideosByQueryUseCase(videoRepository) }

    val actor by lazy { VideoActor(getVideosUseCase, getVideosByQueryUseCase) }

    val reducer by lazy { VideoReducer() }
}