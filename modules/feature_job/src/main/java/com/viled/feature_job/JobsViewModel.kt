package com.viled.feature_job

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.viled.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(
    private val repository: JobsRepository
) : BaseViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val jobs = MutableLiveData<List<Job>>()
    private val cachedJobs: MutableList<Job> = mutableListOf()

    init {
        //  loadJobs()
        //  loadDelayedJobs()
        loadNetworkJobs()

        GlobalScope.async { }
        GlobalScope.launch { }
    }

    fun getJobs(): LiveData<List<Job>> = jobs

    private fun loadRandomJob() {
        compositeDisposable.add(
            repository.loadSingleJob()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { job ->
                    Timber.d("JOB = ${job.id}")
                    cachedJobs.add(job)
                    jobs.postValue(cachedJobs)
                }
        )
    }

    private fun loadDelayedJobs() {
        compositeDisposable.add(
            repository.loadDelayedJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { job ->
                    Timber.d("JOB = ${job.id}")
                    cachedJobs.add(job)
                    jobs.postValue(cachedJobs)
                }
        )
    }

    private fun loadNetworkJobs() {
        compositeDisposable.add(
            repository.loadNetworkJobs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(repository.loadSingleJob().toObservable())
                .subscribe { job ->
                    Timber.d("JOB = ${job.id}")
                    cachedJobs.add(job)
                    jobs.postValue(cachedJobs)
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}