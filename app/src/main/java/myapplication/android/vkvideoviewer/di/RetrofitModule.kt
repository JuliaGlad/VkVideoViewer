package myapplication.android.vkvideoviewer.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import myapplication.android.vkvideoviewer.app.AuthQueryInterceptor
import myapplication.android.vkvideoviewer.data.api.VideoApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class RetrofitModule {

    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(AuthQueryInterceptor())
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()

    @Singleton
    @Provides
    fun provideVideoRetrofit(authClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(
                jsonSerializer.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
            )
            client(authClient)
        }.build()


    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): VideoApi =
        retrofit.create(VideoApi::class.java)

    companion object {
        const val API_KEY = "48922348-ae7a5c63d25e998e6012c84db"
        const val BASE_URL = "https://pixabay.com/api/"
    }

}