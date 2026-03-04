package com.ioffeivan.core.network.utils

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.mapToCommonError
import com.ioffeivan.core.common.error.network.NetworkStatusCode
import java.net.UnknownHostException

internal fun Throwable.mapToAppError(): AppError {
    return when (this) {
        is UnknownHostException -> AppError.NetworkError(statusCode = NetworkStatusCode.NoNetwork)
        else -> mapToCommonError()
    }
}
