package com.viled.feature_job

import com.viled.core.common.base.BaseViewModel
import com.viled.core.common.error.ErrorType
import com.viled.core.dto.Job
import com.viled.network.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val repository: JobsRepository
) : BaseViewModel() {

    private var cachedJobs: List<Job> = mutableListOf()

    sealed class UiState {
        object Loading : UiState()
        class Data(val jobs: List<Job>) : UiState()
        class Error(val errorType: ErrorType) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadJobs()
    }

    private fun loadJobs() {
        doWorkInMainThread(
            doAsyncBlock = {
                val resultDeferred = async(Dispatchers.IO) {
                    repository.loadJobs()
                }

                val result = resultDeferred.await()
                when (result.status) {
                    ResponseStatus.SUCCESS -> {
                        cachedJobs = result.fetchedData!!
                        _uiState.emit(UiState.Data(cachedJobs))
                    }
                    ResponseStatus.ERROR -> _uiState.emit(UiState.Error(result.errorType!!))
                }
            },
            exceptionBlock = { _uiState.value = UiState.Error(it) }
        )
    }
}