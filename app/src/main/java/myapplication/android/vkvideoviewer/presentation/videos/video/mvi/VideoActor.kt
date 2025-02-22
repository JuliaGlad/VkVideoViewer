package myapplication.android.vkvideoviewer.presentation.videos.video.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosByQueryUseCase
import myapplication.android.vkvideoviewer.domain.usecase.video.GetVideosUseCase
import myapplication.android.vkvideoviewer.presentation.main.asyncAwait
import myapplication.android.vkvideoviewer.presentation.main.runCatchingNonCancellation
import myapplication.android.vkvideoviewer.presentation.mvi.MviActor
import myapplication.android.vkvideoviewer.presentation.videos.video.mapper.toUi

class VideoActor(
    private val getVideosUseCase: GetVideosUseCase,
    private val getVideosByQueryUseCase: GetVideosByQueryUseCase
) : MviActor<
        VideoPartialState,
        VideoIntent,
        VideoState,
        VideoEffect>() {

    override fun resolve(
        intent: VideoIntent,
        state: VideoState
    ): Flow<VideoPartialState> =
        when (intent) {
            VideoIntent.GetVideos -> loadVideos(state.page + 1)
            is VideoIntent.GetVideosByQuery -> loadVideosByQuery(intent.query, state.page + 1)
            VideoIntent.Init -> init()
        }

    private fun init() = flow { emit(VideoPartialState.Init) }

    private fun loadVideosByQuery(query: String, page: Int) =
        flow {
            kotlin.runCatching {
                getVideosByQuery(query, page)
            }.fold(
                onSuccess = { data ->
                    emit(VideoPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(VideoPartialState.Error(throwable))
                }
            )
        }

    private fun loadVideos(page: Int) =
        flow {
            kotlin.runCatching {
                getVideos(page)
            }.fold(
                onSuccess = { data ->
                    emit(VideoPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(VideoPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getVideosByQuery(query: String, page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getVideosByQueryUseCase.invoke(query, page) }
            ){ data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getVideos(page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getVideosUseCase.invoke(page) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()
}