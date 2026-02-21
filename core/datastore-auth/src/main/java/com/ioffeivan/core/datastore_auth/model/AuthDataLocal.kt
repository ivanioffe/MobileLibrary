package com.ioffeivan.core.datastore_auth.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthDataLocal(
    val accessToken: String? = null,
)
