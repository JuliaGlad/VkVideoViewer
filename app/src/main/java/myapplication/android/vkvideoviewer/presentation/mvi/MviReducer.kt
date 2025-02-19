package myapplication.android.vkvideoviewer.presentation.mvi

interface MviReducer<PartialState: MviPartialState, State: MviState> {

    fun reduce(prevState: State, partialState: PartialState): State
}