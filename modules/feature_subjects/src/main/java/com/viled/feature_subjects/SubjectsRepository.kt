package com.viled.feature_subjects

import com.viled.core.dto.Subject
import com.viled.network.NetworkApi
import com.viled.network.NetworkResult
import com.viled.network.ResponseHandler
import javax.inject.Inject

class SubjectsRepository @Inject constructor(private val api: NetworkApi) {

    suspend fun getSubjects(): NetworkResult<List<Subject>> {
        val result = api.getTags()
        return ResponseHandler.handleResponse(result)
    }
}