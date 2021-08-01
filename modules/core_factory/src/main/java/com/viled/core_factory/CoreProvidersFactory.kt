package com.viled.core_factory

import com.viled.core.DaggerViewModelComponent
import com.viled.core.ViewModelsProvider

object CoreProvidersFactory {

//    fun createDatabaseBuilder(appProvider: AppProvider): DatabaseProvider =
//            DaggerDatabaseComponent.builder().appProvider(appProvider).build()


    fun createViewModelBuilder(): ViewModelsProvider {
        return DaggerViewModelComponent.create()
    }
}