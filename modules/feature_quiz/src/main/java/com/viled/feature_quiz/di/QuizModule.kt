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

//    @Binds
//    @Singleton
//    abstract fun bindsRepository(repository: QuizRepository): QuizRepository


//    @Singleton
    @Provides
    fun providesQuizRepository(networkApi: NetworkApi): QuizRepository = QuizRepository(networkApi)


//    @Singleton
//    @Provides
//    fun providesQuizViewModel(quizRepository: QuizRepository): QuizViewModel =
//        QuizViewModel(quizRepository)


//    companion object {
//
//        @Provides
//        @Singleton
//        @JvmStatic
//        fun provideQuizViewModel(
//            map: @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>,
//            quizRepository: QuizRepository
//        ): ViewModel = QuizViewModel(quizRepository).also {
//            map[QuizViewModel::class.java] = it
//        }
//    }
}