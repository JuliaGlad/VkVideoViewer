package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviIntent

sealed interface ImagesIntent: MviIntent {

    data object Init: ImagesIntent

    data object GetImages: ImagesIntent

    data class GetImagesByCategory(val category: String): ImagesIntent
}