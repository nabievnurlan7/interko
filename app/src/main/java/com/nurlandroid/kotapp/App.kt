package com.nurlandroid.kotapp

import android.app.Application
import com.viled.core.AppWithFacade
import com.viled.core.ProvidersFacade

class KotApplication : Application(), AppWithFacade {

    override fun onCreate() {
        super.onCreate()

        (getFacade() as FacadeComponent).inject(this)
    }

    override fun getFacade(): ProvidersFacade = facadeComponent ?: FacadeComponent.init(this).also {
        facadeComponent = it
    }

    companion object {
        private var facadeComponent: FacadeComponent? = null
    }
}