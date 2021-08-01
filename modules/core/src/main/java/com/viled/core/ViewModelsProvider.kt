package com.viled.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface ViewModelsProvider {

    fun provideMap(): @JvmSuppressWildcards MutableMap<Class<out ViewModel>, ViewModel>

    fun provideViewModel(): ViewModelProvider.Factory
}