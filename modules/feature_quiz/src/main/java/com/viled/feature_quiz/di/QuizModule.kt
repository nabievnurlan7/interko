package com.viled.feature_quiz.di

import androidx.lifecycle.ViewModel
import com.viled.feature_quiz.QuizRepository
import com.viled.feature_quiz.QuizViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class QuizModule {

    @Binds
    @Singleton
    abstract fun bindsRepository(repository: QuizRepository): QuizRepository

    companion object {

        @Provides
        @Singleton
        @JvmStatic
        fun provideQuizViewModel(
            map: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>,
            quizRepository: QuizRepository
        ): ViewModel = QuizViewModel(quizRepository).also {
            map[QuizViewModel::class.java] = it
        }
    }
}