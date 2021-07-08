package com.nurlandroid.kotapp.di.dagger

import android.app.Application
import com.nurlandroid.kotapp.KotApplication
import com.nurlandroid.kotapp.NetworkApi
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            MainActivityModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: KotApplication)
}