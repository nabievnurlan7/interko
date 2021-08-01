package com.nurlandroid.kotapp

import android.app.Application
import com.viled.core.AppProvider
import com.viled.core.ProvidersFacade
import dagger.Component

@Component(
    dependencies = [AppProvider::class]
)
interface FacadeComponent : ProvidersFacade {

    companion object {

        fun init(application: Application): FacadeComponent =
            DaggerFacadeComponent.builder()
                .appProvider(AppComponent.create(application))
                //   .databaseProvider(CoreProvidersFactory.createDatabaseBuilder(AppComponent.create(application)))
                .build()
    }

    fun inject(app: KotApplication)
}