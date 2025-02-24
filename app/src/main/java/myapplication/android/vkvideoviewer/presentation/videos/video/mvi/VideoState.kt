package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviState
import myapplication.android.vkvideoviewer.presentation.videos.model.VideoUiList

data class VideoState(
    val ui: LceState<VideoUiList>,
    val page: Int = 0
): MviState