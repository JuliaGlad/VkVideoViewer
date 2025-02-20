package myapplication.android.vkvideoviewer.presentation.player.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosQualitiesUseCase
import myapplication.android.vkvideoviewer.domain.usecase.GetVideosUseCase
import myapplication.android.vkvideoviewer.presentation.main.asyncAwait
import myapplication.android.vkvideoviewer.presentation.main.runCatchingNonCancellation
import myapplication.android.vkvideoviewer.presentation.mvi.MviActor
import myapplication.android.vkvideoviewer.presentation.player.mapper.toUi
import myapplication.android.vkvideoviewer.presentation.player.model.PlayerResult
import myapplication.android.vkvideoviewer.presentation.video.mapper.toUi

class PlayerActor(
    private val getVideosUseCase: GetVideosUseCase,
    private val getVideosQualitiesUseCase: GetVideosQualitiesUseCase
) : MviActor<
        PlayerPartialState,
        PlayerIntent,
        PlayerState,
        PlayerEffect>() {
    override fun resolve(
        intent: PlayerIntent,
        state: PlayerState
    ): Flow<PlayerPartialState> =
        when (intent) {
            is PlayerIntent.GetVideos -> loadVideosAndQualities(
                intent.videoId,
                intent.videoPage,
                state.page + 1
            )

            PlayerIntent.GetNewVideos -> loadVideos(state.page + 1)
        }

    private fun loadVideos(page: Int) =
        flow {
            kotlin.runCatching {
                getVideos(page)
            }.fold(
                onSuccess = { data ->
                    emit(PlayerPartialState.NewVideosLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PlayerPartialState.Error(throwable))
                }
            )
        }

    private fun loadVideosAndQualities(videoId: Int, videoPage: Int, page: Int) =
        flow {
            kotlin.runCatching {
                PlayerResult(
                    getQualities(videoPage, videoId),
                    getVideos(page)
                )
            }.fold(
                onSuccess = { data ->
                    emit(PlayerPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(PlayerPartialState.Error(throwable))
                }
            )
        }

    private suspend fun getQualities(videoPage: Int, videoId: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getVideosQualitiesUseCase.invoke(videoPage, videoId) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

    private suspend fun getVideos(page: Int) =
        runCatchingNonCancellation {
            asyncAwait(
                { getVideosUseCase.invoke(page) },
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()
}