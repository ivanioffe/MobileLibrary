package com.ioffeivan.core.common.result

sealed class DataResult<out D, out E> {
    data class Success<out D>(val data: D) : DataResult<D, Nothing>()

    data class Error<out E>(val error: E) : DataResult<Nothing, E>()
}
