package com.nurlandroid.kotapp.di

import com.nurlandroid.kotapp.NetworkApi
import com.nurlandroid.kotapp.common.CustomFragmentFactory
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val diModule = module {
    single { CustomFragmentFactory() }

    single<NetworkApi> {
        Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
    }
}