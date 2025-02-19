package myapplication.android.vkvideoviewer.presentation.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviState
import myapplication.android.vkvideoviewer.presentation.video.model.VideoUiList

data class VideoState(val ui: LceState<VideoUiList>, val page: Int = 0): MviState