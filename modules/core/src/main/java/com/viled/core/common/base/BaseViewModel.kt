package com.viled.core.common.base

import androidx.lifecycle.ViewModel
import com.viled.core.common.error.ErrorHandler
import com.viled.core.common.error.ErrorType
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    fun <T> doWorkInIO(
        doAsyncBlock: suspend CoroutineScope.() -> T,
        exceptionBlock: (ErrorType) -> Unit
    ) {
        doCoroutineWork(doAsyncBlock, viewModelScope, IO, exceptionBlock)
    }

    fun <T> doWorkInMainThread(
        doAsyncBlock: suspend CoroutineScope.() -> T,
        exceptionBlock: (ErrorType) -> Unit
    ) {
        doCoroutineWork(doAsyncBlock, viewModelScope, Main, exceptionBlock)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private inline fun <T> doCoroutineWork(
        crossinline doAsyncBlock: suspend CoroutineScope.() -> T,
        scope: CoroutineScope,
        context: CoroutineContext,
        crossinline exceptionBlock: (ErrorType) -> Unit
    ) {
        scope.launch(
            CoroutineExceptionHandler { _, throwable ->
                exceptionBlock.invoke(ErrorHandler.handleException(throwable))
            }
        )
        {
            withContext(context) {
                doAsyncBlock.invoke(this)
            }
        }
    }
}