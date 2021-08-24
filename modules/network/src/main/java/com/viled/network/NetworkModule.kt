package com.viled.network

import com.viled.core.common.SERVER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object NetworkModule {

    @Provides
    fun providesNetworkApi(): NetworkApi = Retrofit.Builder()
        .baseUrl(SERVER)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)
}