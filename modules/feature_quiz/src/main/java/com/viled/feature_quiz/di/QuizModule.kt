package com.viled.feature_quiz.di

import com.viled.feature_quiz.QuizRepository
import com.viled.feature_quiz.QuizViewModel
import com.viled.network.NetworkApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Singleton

@InstallIn(ActivityRetainedComponent::class)
@Module
object QuizModule {

    @Provides
    fun providesQuizRepository(networkApi: NetworkApi): QuizRepository = QuizRepository(networkApi)
}