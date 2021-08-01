package com.viled.network

import com.viled.core.dto.Question
import com.viled.core.dto.Subject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("questions")
    suspend fun getQuestions(
        @Query("quantity") quantity: Int,
        @Query("tag") tagId: Int
    ): Response<List<Question>>

    @GET("tags")
    suspend fun getTags(): List<Subject>
}