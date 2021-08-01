package com.viled.core

import android.content.Context

interface AppProvider {

    fun provideContext(): Context
}