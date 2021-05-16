package com.nurlandroid.kotapp

import retrofit2.Response
import retrofit2.http.GET

interface NetworkApi {

    @GET("posts")
    suspend fun getQuestions(): Response<List<Any>>
}