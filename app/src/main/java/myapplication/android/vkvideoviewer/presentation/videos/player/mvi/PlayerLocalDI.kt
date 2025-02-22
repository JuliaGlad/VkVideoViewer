package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosQualitiesUseCase
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosUseCase
import javax.inject.Inject

class PlayerLocalDI @Inject constructor(
    private val videoRepository: VideoRepository
) {
    private val getVideosQualitiesUseCase by lazy { GetVideosQualitiesUseCase(videoRepository) }

    private val getVideosUseCase by lazy { GetVideosUseCase(videoRepository) }

    val actor by lazy { PlayerActor(getVideosUseCase, getVideosQualitiesUseCase) }

    val reducer by lazy { PlayerReducer() }
}