package myapplication.android.vkvideoviewer.di.component.activity

import dagger.Component
import myapplication.android.vkvideoviewer.di.AppComponent
import myapplication.android.vkvideoviewer.presentation.main.MainActivity
import javax.inject.Scope

@MainActivityScope
@Component(dependencies = [AppComponent::class])
interface MainActivityComponent {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): MainActivityComponent
    }
}

@Scope
annotation class MainActivityScope