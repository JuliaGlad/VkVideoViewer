package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import myapplication.android.vkvideoviewer.presentation.mvi.LceState
import myapplication.android.vkvideoviewer.presentation.mvi.MviStore

class VideoStore(
    reducer: VideoReducer,
    actor: VideoActor
): MviStore<
        VideoPartialState,
        VideoIntent,
        VideoState,
        VideoEffect>(reducer, actor) {
    override fun initialStateCreator():  VideoState =  VideoState(ui = LceState.Loading)
}