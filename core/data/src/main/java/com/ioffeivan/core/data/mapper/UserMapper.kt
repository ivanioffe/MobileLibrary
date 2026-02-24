package com.ioffeivan.core.data.mapper

import com.ioffeivan.core.datastore_user.model.UserLocal
import com.ioffeivan.core.model.User

fun UserLocal.toUser(): User {
    return User(
        name = name,
        email = email,
        photoUri = photoUri,
    )
}

fun User.toUserLocal(): UserLocal {
    return UserLocal(
        name = name,
        email = email,
        photoUri = photoUri,
    )
}
