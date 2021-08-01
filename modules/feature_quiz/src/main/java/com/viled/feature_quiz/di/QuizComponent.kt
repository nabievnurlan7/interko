package com.viled.feature_quiz.di

import com.viled.core.ProvidersFacade
import com.viled.core.ViewModelsProvider
import com.viled.core_factory.CoreProvidersFactory
import com.viled.feature_quiz.QuizFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [QuizModule::class],
    dependencies = [ProvidersFacade::class, ViewModelsProvider::class]
)
interface QuizComponent : ViewModelsProvider{

    companion object {

        fun create(providersFacade: ProvidersFacade): QuizComponent {
            return DaggerQuizComponent
                .builder()
                .viewModelsProvider(CoreProvidersFactory.createViewModelBuilder())
                .providersFacade(providersFacade)
                .build()
        }
    }

    fun inject(quizFragment: QuizFragment)
}