package com.nurlandroid.kotapp.di.dagger

import com.nurlandroid.kotapp.feature.quiz.QuizFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeQuizFragment(): QuizFragment
}