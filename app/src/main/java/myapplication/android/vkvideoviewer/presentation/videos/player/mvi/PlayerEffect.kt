package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviEffect
import myapplication.android.vkvideoviewer.presentation.videos.player.model.PlayerArguments

sealed interface PlayerEffect: MviEffect {

    data object FinishActivity: PlayerEffect

    class OpenAnotherVideo(
        val playerArguments: PlayerArguments
    ): PlayerEffect
}