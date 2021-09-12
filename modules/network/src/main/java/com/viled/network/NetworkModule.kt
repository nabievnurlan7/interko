package com.viled.network

import com.viled.core.common.SERVER
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesNetworkApi(): NetworkApi = Retrofit.Builder()
        .baseUrl(SERVER)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)
}