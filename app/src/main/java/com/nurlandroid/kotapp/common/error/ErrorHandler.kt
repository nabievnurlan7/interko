package com.nurlandroid.kotapp.common.error

object ErrorHandler {
    fun handleErrorByCode(code: Int): ErrorType =
            when (code) {
                2 -> ErrorType.BODY_IS_NULL
                3 -> ErrorType.NO_CATALOG
                401 -> ErrorType.NOT_AUTHORIZED
                403 -> ErrorType.ACCESS_DENIED
                else -> ErrorType.UNKNOWN
            }
}

enum class ErrorType(val code: Int, val message: String) {
    UNKNOWN(1, "Unknown error"),
    BODY_IS_NULL(2, "Response body is NULL"),
    NO_CATALOG(3, "No catalog"),
    NOT_AUTHORIZED(401, "User is not authorized"),
    ACCESS_DENIED(403, "Access denied")
}