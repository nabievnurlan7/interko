package com.viled.feature_quiz.di

import com.viled.feature_quiz.quiz_main.QuizRepository
import com.viled.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@InstallIn(ActivityRetainedComponent::class)
@Module
object QuizModule {

    @Provides
    fun providesQuizRepository(networkApi: NetworkApi): QuizRepository = QuizRepository(networkApi)
}