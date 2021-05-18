package com.nurlandroid.kotapp.common.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    fun <T> doWorkInIO(doAsyncBlock: suspend CoroutineScope.() -> T) {
        doCoroutineWork(doAsyncBlock, viewModelScope, IO)
    }

    fun <T> doWorkInMainThread(doAsyncBlock: suspend CoroutineScope.() -> T) {
        doCoroutineWork(doAsyncBlock, viewModelScope, Main)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private inline fun <T> doCoroutineWork(
        crossinline doAsyncBlock: suspend CoroutineScope.() -> T,
        scope: CoroutineScope,
        context: CoroutineContext
    ) {
        scope.launch {
            withContext(context) {
                doAsyncBlock.invoke(this)
            }
        }
    }
}