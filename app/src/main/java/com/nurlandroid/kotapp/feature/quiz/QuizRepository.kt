package com.nurlandroid.kotapp.feature.quiz

import com.nurlandroid.kotapp.NetworkApi
import com.nurlandroid.kotapp.common.network.NetworkResult
import com.nurlandroid.kotapp.common.network.ResponseHandler

class QuizRepository(private val api: NetworkApi) {

    suspend fun getQuestions(quantity: Int, tagId: Int): NetworkResult<List<Question>> {
        val result = api.getQuestions(quantity, tagId)
        return ResponseHandler.handleResponse(result)
    }
}