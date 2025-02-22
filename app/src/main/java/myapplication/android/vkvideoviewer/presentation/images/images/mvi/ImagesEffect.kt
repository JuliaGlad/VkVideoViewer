package myapplication.android.vkvideoviewer.presentation.images.images.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviEffect

sealed interface ImagesEffect: MviEffect{

    data class OpenFullScreenImage(
        val url: String
    ): ImagesEffect

}