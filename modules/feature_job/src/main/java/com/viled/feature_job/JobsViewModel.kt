package com.viled.feature_job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.viled.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    //private val repository: NetworkRepository
) : BaseViewModel() {
    private val tags = MutableLiveData<List<Job>>()

    init {
        loadJobs()
    }

    fun getJobs(): LiveData<List<Job>> = tags

    private fun loadJobs() {
        doWorkInIO(
            doAsyncBlock = {
         //       val result = repository.getJobs().loadedData ?: emptyList()
       //         tags.postValue(result)
            },
            exceptionBlock = {})
    }
}