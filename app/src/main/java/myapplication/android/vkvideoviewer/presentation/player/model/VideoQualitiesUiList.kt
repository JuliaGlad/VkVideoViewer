package myapplication.android.vkvideoviewer.presentation.player.model

class VideoQualitiesUiList(
    val large: VideoQualityUiModel,
    val medium: VideoQualityUiModel,
    val small: VideoQualityUiModel,
    val tiny: VideoQualityUiModel
)

class VideoQualityUiModel(
    val url: String
)