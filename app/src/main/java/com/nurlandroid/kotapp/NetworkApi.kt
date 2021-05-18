package com.nurlandroid.kotapp

import com.nurlandroid.kotapp.feature.Subject
import com.nurlandroid.kotapp.feature.quiz.Question
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