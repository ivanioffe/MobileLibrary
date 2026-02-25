package com.ioffeivan.feature.sign_in.data.source.local.data_source

import com.ioffeivan.core.datastore_user.UserManager
import com.ioffeivan.core.datastore_user.model.UserLocal
import javax.inject.Inject

class DataStoreUserLocalDataSource @Inject constructor(
    private val userManager: UserManager,
) : UserLocalDataSource {
    override suspend fun saveUser(userLocal: UserLocal) {
        userManager.saveUser(userLocal)
    }
}
