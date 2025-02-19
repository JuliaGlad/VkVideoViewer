package myapplication.android.vkvideoviewer.domain.usecase

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.mapper.toDomain
import myapplication.android.vkvideoviewer.domain.models.VideosDomainList
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val videoRepository: VideoRepository
){
    suspend fun invoke(page: Int): VideosDomainList =
        videoRepository.getVideos(page).toDomain()
}