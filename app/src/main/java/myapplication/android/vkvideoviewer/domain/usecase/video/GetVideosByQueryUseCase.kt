package myapplication.android.vkvideoviewer.domain.usecase.video

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.mapper.video.toDomain
import myapplication.android.vkvideoviewer.domain.models.video.VideosDomainList
import javax.inject.Inject

class GetVideosByQueryUseCase @Inject constructor(
    private val videoRepository: VideoRepository
){
    suspend fun invoke(query: String, page: Int): VideosDomainList =
        videoRepository.getVideosByQuery(query, page).toDomain(query)
}