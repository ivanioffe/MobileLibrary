package com.ioffeivan.core.datastore_user

import androidx.datastore.core.DataStore
import com.ioffeivan.core.datastore_user.model.UserLocal
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserManager @Inject constructor(
    private val dataStore: DataStore<UserLocal>,
) {
    fun getUser(): Flow<UserLocal> = dataStore.data

    suspend fun saveUser(userLocal: UserLocal) {
        dataStore.updateData { userLocal }
    }

    suspend fun deleteUser() {
        dataStore.updateData { UserLocal() }
    }
}
