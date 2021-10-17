package com.viled.feature_subjects.di

import com.viled.feature_subjects.SubjectsRepository
import com.viled.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object SubjectsModule {

    @Provides
    fun providesSubjectsRepository(networkApi: NetworkApi): SubjectsRepository =
        SubjectsRepository(networkApi)
}