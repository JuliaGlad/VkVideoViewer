package myapplication.android.vkvideoviewer.domain.usecase.video

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.mapper.video.toDomain
import myapplication.android.vkvideoviewer.domain.models.video.VideoQualityDomainList
import javax.inject.Inject

class GetVideosQualitiesUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend fun invoke(page: Int, id: Int): VideoQualityDomainList =
        videoRepository.getVideoQuality(page, id).toDomain()
}