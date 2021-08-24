package com.viled.feature_quiz

import com.viled.core.common.network.NetworkResult
import com.viled.core.common.network.ResponseHandler
import com.viled.core.dto.Question
import com.viled.network.NetworkApi
import javax.inject.Inject

class QuizRepository @Inject constructor(private val api: NetworkApi) {


    suspend fun getQuestions(quantity: Int, tagId: Int): NetworkResult<List<Question>> {
        val result = api.getQuestions(quantity, tagId)
        return ResponseHandler.handleResponse(result)
    }
}