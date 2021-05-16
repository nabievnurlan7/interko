package com.nurlandroid.kotapp

import com.nurlandroid.kotapp.feature.Subject
import com.nurlandroid.kotapp.feature.Question
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {

    @GET("questions")
    suspend fun getQuestions(
            @Query("quantity") quantity: Int,
            @Query("tag") tagId: Int
    ): List<Question>

    @GET("tags")
    suspend fun getTags(): List<Subject>
}