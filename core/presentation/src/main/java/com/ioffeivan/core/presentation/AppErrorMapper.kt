package com.ioffeivan.core.presentation

import androidx.annotation.StringRes
import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.common.CommonStatusCode
import com.ioffeivan.core.common.error.network.NetworkStatusCode

@StringRes
fun AppError.asStringRes(): Int {
    return when (this) {
        is AppError.NetworkError -> this.statusCode.toStringRes()
        is AppError.CommonError -> this.statusCode.toStringRes()
    }
}

@StringRes
private fun NetworkStatusCode.toStringRes(): Int {
    return when (this) {
        NetworkStatusCode.NoNetwork -> R.string.error_network_no_connection
        else -> R.string.error_network_unknown
    }
}

@StringRes
private fun CommonStatusCode.toStringRes(): Int {
    return when (this) {
        else -> R.string.error_network_unknown
    }
}
