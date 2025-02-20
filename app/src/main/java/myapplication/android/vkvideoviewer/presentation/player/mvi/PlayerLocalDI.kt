package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosQualitiesUseCase
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosUseCase
import javax.inject.Inject

class PlayerLocalDI @Inject constructor(
    private val videoRepository: VideoRepository
) {
    private val getVideosQualitiesUseCase by lazy { GetVideosQualitiesUseCase(videoRepository) }

    private val getVideosUseCase by lazy { GetVideosUseCase(videoRepository) }

    val actor by lazy { PlayerActor(getVideosUseCase, getVideosQualitiesUseCase) }

    val reducer by lazy { PlayerReducer() }
}