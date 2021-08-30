package com.viled.network

import com.viled.core.common.error.ErrorType

class NetworkResult<out T>(
    val status: ResponseStatus,
    val fetchedData: T?,
    val errorType: ErrorType?
) {
    companion object {
        fun <T> success(fetchedData: T?): NetworkResult<T> =
                NetworkResult(status = ResponseStatus.SUCCESS, fetchedData = fetchedData, errorType = null)

        fun <T> error(errorType: ErrorType): NetworkResult<T> =
                NetworkResult(status = ResponseStatus.ERROR, fetchedData = null, errorType = errorType)
    }
}