package com.nurlandroid.kotapp.di.dagger


import com.nurlandroid.kotapp.feature.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
            modules = [
                FragmentBuildersModule::class
            ]
    )
    abstract fun contributeMainActivity(): MainActivity
}