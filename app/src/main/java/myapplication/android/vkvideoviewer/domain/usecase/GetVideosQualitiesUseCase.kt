package myapplication.android.vkvideoviewer.domain.usecase

import myapplication.android.vkvideoviewer.data.repository.VideoRepository
import myapplication.android.vkvideoviewer.domain.mapper.toDomain
import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainList
import myapplication.android.vkvideoviewer.domain.models.VideoQualityDomainModel
import javax.inject.Inject

class GetVideosQualitiesUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    fun invoke(id: Int): VideoQualityDomainList =
        videoRepository.getVideoQuality(id).toDomain()
}