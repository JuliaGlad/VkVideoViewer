package myapplication.android.vkvideoviewer.presentation.video.mvi

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosByQueryUseCase
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosUseCase
import javax.inject.Inject

class VideoLocalDI @Inject constructor(
    private val videoRepository: VideoRepository
)  {

    private val getVideosUseCase by lazy { GetVideosUseCase(videoRepository) }

    private val getVideosByQueryUseCase by lazy { GetVideosByQueryUseCase(videoRepository) }

    val actor by lazy { VideoActor(getVideosUseCase, getVideosByQueryUseCase) }

    val reducer by lazy { VideoReducer() }
}