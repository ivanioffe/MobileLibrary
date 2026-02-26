package com.ioffeivan.feature.sign_in.data.mapper

import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import com.ioffeivan.feature.sign_in.domain.model.AuthData

fun AuthData.toAuthDataLocal(): AuthDataLocal {
    return AuthDataLocal(
        accessToken = accessToken,
    )
}
