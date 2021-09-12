package com.viled.feature_main

import com.viled.network.NetworkApi
import com.viled.network.NetworkResult
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: NetworkApi) {

    suspend fun login(phone: String, password: String): NetworkResult<String> {
        return NetworkResult.success("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    }
}