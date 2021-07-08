package com.nurlandroid.kotapp.di

import com.nurlandroid.kotapp.NetworkApi
import com.nurlandroid.kotapp.common.CustomFragmentFactory
import com.nurlandroid.kotapp.common.SERVER
import com.nurlandroid.kotapp.feature.quiz.QuizRepository
import com.nurlandroid.kotapp.feature.quiz.QuizViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val diModule = module {
    single { CustomFragmentFactory() }
//
//    single { QuizRepository(get()) }
//    viewModel { QuizViewModel(get()) }

    single<NetworkApi> {
        Retrofit.Builder()
                .baseUrl(SERVER)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(NetworkApi::class.java)
    }
}