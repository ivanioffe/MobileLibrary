package com.ioffeivan.core.network.utils

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.network.NetworkStatusCode
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.ErrorResponseDto
import retrofit2.Response

internal fun <T> Response<T>.toDataResult(): DataResult<T, AppError> {
    val body = body()

    return if (isSuccessful) {
        if (body != null) {
            DataResult.Success(data = body)
        } else {
            DataResult.Error(AppError.NetworkError(statusCode = NetworkStatusCode.NoContent))
        }
    } else {
        val code = NetworkStatusCode.from(code())
        val errorType =
            errorBody()?.let {
                parseErrorType(it.string())
            }

        DataResult.Error(
            error =
                AppError.NetworkError(
                    statusCode = code,
                    type = errorType,
                ),
        )
    }
}

private fun parseErrorType(errorBody: String): String {
    val errorResponse = NetworkJson.json.decodeFromString<ErrorResponseDto>(errorBody)

    return errorResponse.error.errors?.first()?.message ?: errorResponse.error.message
}
