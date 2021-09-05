package com.viled.feature_job

import com.viled.network.NetworkApi
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class JobsRepository @Inject constructor(private val api: NetworkApi) {

    //    Реализуйте функцию так, чтобы она возвращала Fact со случайной строкой из массива строк R.array.local_cat_facts обернутую в подходящий стрим(Flowable/Single/Observable и т.п)
    fun loadSingleJob(): Single<Job> = Single.just(getRandomJob())

    //    Реализуйте функцию так, чтобы она эмитила Fact со случайной строкой из массива строк R.array.local_cat_facts каждые 2000 миллисекунд. Если вновь заэмиченный Fact совпадает с предыдущим - пропускаем элемент.
    fun loadDelayedJobs(): Observable<Job> =
        Observable.interval(2, TimeUnit.SECONDS)
            .map { getRandomJob() }
            .distinct()

    // Реализуйте функцию: каждые 2 секунды идем в сеть за новым фактом, если сетевой запрос завершился неуспешно, то в качестве фоллбека идем за фактом в уже реализованный otus.homework.reactivecats.LocalCatFactsGenerator#generateCatFact.
    fun loadNetworkJobs(): Observable<Job> =
        Observable.interval(2, TimeUnit.SECONDS)
            .map { getForeignRandomJob() }
            .map {
                // Imitation of Exception for purpose of exercise
                it.id.toInt()
                it
            }


    // For purpose of exercise
    private fun getRandomJob(): Job = MockData.getLocalJobs().random()
    private fun getForeignRandomJob(): Job = MockData.getForeignJobs().random()
}