package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.MviEffect

sealed interface PlayerEffect: MviEffect {

    data object FinishActivity: PlayerEffect
}