package com.viled.core.di

import android.content.Context
import android.os.Build
import com.viled.core.common.SharedPrefLayer
import com.viled.core.common.crypto.CryptoUtils
import com.viled.core.common.crypto.CryptoUtilsLowerMReified
import com.viled.core.common.crypto.CryptoUtilsReified
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun providesSharedPrefLayer(@ApplicationContext appContext: Context): SharedPrefLayer =
        SharedPrefLayer(appContext)

    @Singleton
    @Provides
    fun provideCryptoUtils(@ApplicationContext appContext: Context): CryptoUtils =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) CryptoUtilsReified()
        else CryptoUtilsLowerMReified(appContext)
}