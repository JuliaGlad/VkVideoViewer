package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.images.images.model.ImageUiList
import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviState

data class ImagesState(val ui: LceState<ImageUiList>, val page: Int = 0): MviState