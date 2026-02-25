package com.ioffeivan.feature.sign_in.data.source.local.data_source

import com.ioffeivan.core.datastore_user.model.UserLocal

interface UserLocalDataSource {
    suspend fun saveUser(userLocal: UserLocal)
}
