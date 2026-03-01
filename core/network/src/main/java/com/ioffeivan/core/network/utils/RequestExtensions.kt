package com.ioffeivan.core.network.utils

import okhttp3.Request

private const val AUTHORIZATION_HEADER = "Authorization"

internal fun Request.Builder.authorizationHeader(token: String): Request.Builder =
    header(AUTHORIZATION_HEADER, "Bearer $token")

internal fun Request.getTokenFromAuthorizationHeader(): String? {
    return header(AUTHORIZATION_HEADER)?.replace("Bearer ", "")
}
