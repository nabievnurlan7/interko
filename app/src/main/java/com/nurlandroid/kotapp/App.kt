package com.nurlandroid.kotapp

import android.app.Application
import com.nurlandroid.kotapp.di.diModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class KotApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KotApplication)
            androidLogger()
            modules(diModule)
        }
    }
}