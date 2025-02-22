package myapplication.android.vkvideoviewer.presentation.videos.player.model

class VideoQualitiesUiList(
    val medium: VideoQualityUiModel,
    val small: VideoQualityUiModel,
    val tiny: VideoQualityUiModel
)

class VideoQualityUiModel(
    val url: String,
    val size: Int
)