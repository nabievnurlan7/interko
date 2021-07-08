package com.nurlandroid.kotapp

import android.app.Activity
import android.app.Application
import com.nurlandroid.kotapp.di.dagger.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class KotApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
//        startKoin {
//            androidContext(this@KotApplication)
//            androidLogger()
//            modules(diModule)
//        }
    }

    override fun activityInjector() = dispatchingAndroidInjector
}