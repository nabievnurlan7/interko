package com.viled.network

import com.viled.core.common.error.ErrorHandler
import com.viled.core.common.error.ErrorType
import okhttp3.internal.http.promisesBody
import retrofit2.Response

object ResponseHandler {
    fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        require(response.code() == 200) {
            val errorCode = response.code()
            return NetworkResult.error(errorType = ErrorHandler.handleServerErrorByCode(errorCode))
        }

        val body = response.body()
        val isBodyExists = response.raw().promisesBody()

        return if (body != null && isBodyExists) {
            NetworkResult.success(fetchedData = body)
        } else if (body == null && response.code() == 200) {
            NetworkResult.error(errorType = ErrorType.BODY_IS_NULL)
        } else {
            NetworkResult.error(errorType = ErrorType.UNKNOWN)
        }
    }
}