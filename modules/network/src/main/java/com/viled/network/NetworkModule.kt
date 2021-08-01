package com.viled.network

import com.viled.core.common.SERVER
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkApi(): NetworkApi = Retrofit.Builder()
        .baseUrl(SERVER)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(NetworkApi::class.java)
}