package com.ioffeivan.feature.sign_in.presentation.mapper

import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.ioffeivan.core.model.User

fun GoogleIdTokenCredential.toUser(): User {
    return User(
        name = displayName,
        email = email,
        photoUri = profilePictureUri.toString(),
    )
}
