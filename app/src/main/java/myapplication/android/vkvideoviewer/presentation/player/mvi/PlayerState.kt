package myapplication.android.vkvideoviewer.presentation.player.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviState
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerResult

data class PlayerState(val ui: LceState<PlayerResult>, val page: Int = 0): MviState