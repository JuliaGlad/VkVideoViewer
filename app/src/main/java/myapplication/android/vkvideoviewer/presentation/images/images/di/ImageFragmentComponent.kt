package myapplication.android.vkvideoviewer.presentation.images.images.di

import dagger.Component
import myapplication.android.vkvideoviewer.di.AppComponent
import myapplication.android.vkvideoviewer.presentation.images.images.ImagesFragment
import javax.inject.Scope

@ImageFragmentScope
@Component(
    modules = [
        ImageFragmentModule::class,
        ImageFragmentLocalDIModule::class
    ],
    dependencies = [AppComponent::class]
)
interface ImageFragmentComponent {

    fun inject(fragment: ImagesFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): ImageFragmentComponent
    }
}

@Scope
annotation class ImageFragmentScope