package myapplication.android.vkvideoviewer.presentation.videos.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviEffect

sealed interface PlayerEffect: MviEffect {

    data object FinishActivity: PlayerEffect

    data object OpenAnotherVideo: PlayerEffect
}