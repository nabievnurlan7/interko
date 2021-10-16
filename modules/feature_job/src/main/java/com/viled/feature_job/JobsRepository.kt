package com.viled.feature_job

import com.viled.core.dto.Job
import com.viled.network.NetworkApi
import com.viled.network.NetworkResult
import com.viled.network.ResponseHandler
import javax.inject.Inject

class JobsRepository @Inject constructor(private val api: NetworkApi) {

    suspend  fun loadJobs(): NetworkResult<List<Job>> {
        val result = api.getJobs()
        return ResponseHandler.handleResponse(result)
    }
}