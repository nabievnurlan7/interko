package com.nurlandroid.kotapp.di.dagger

import dagger.Module

@Module(includes = [ViewModelModule::class, NetworkModule::class])
class AppModule {
}