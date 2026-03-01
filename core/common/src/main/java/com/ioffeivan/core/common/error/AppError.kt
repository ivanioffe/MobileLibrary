package com.ioffeivan.core.common.error

import com.ioffeivan.core.common.error.common.CommonStatusCode
import com.ioffeivan.core.common.error.network.NetworkErrorType
import com.ioffeivan.core.common.error.network.NetworkStatusCode

/**
 * Represents the base hierarchy for all application-level errors.
 */
sealed class AppError {
    /**
     * Errors originating from the network layer, including HTTP status and specific error types.
     */
    data class NetworkError(
        val statusCode: NetworkStatusCode,
        val type: NetworkErrorType? = null,
    ) : AppError()

    /**
     * General or system-level errors not directly related to networking or business logic.
     */
    data class CommonError(
        val statusCode: CommonStatusCode,
    ) : AppError()
}

fun Throwable.mapToCommonError(): AppError.CommonError {
    val statusCode =
        when (this) {
            is NullPointerException -> CommonStatusCode.NullPointerException
            is IllegalStateException -> CommonStatusCode.IllegalStateException
            is IllegalArgumentException -> CommonStatusCode.IllegalArgumentException
            is ArrayIndexOutOfBoundsException -> CommonStatusCode.ArrayIndexOutOfBoundsException
            else -> CommonStatusCode.Unknown
        }

    return AppError.CommonError(
        statusCode = statusCode,
    )
}
