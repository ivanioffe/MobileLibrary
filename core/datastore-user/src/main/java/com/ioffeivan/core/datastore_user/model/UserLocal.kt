package com.ioffeivan.core.datastore_user.model

import kotlinx.serialization.Serializable

@Serializable
data class UserLocal(
    val name: String? = null,
    val email: String? = null,
    val photoUri: String? = null,
)
