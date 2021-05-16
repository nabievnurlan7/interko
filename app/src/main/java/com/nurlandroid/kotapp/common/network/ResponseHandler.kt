package com.nurlandroid.kotapp.common.network

import com.nurlandroid.kotapp.common.error.ErrorHandler
import com.nurlandroid.kotapp.common.error.ErrorType
import okhttp3.internal.http.promisesBody
import retrofit2.Response

object ResponseHandler {
    fun <T> handleResponse(response: Response<T>): NetworkResult<T> {
        require(response.code() == 200) {
            val errorCode = response.code()
            return NetworkResult.error(errorType = ErrorHandler.handleErrorByCode(errorCode))
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