package com.ioffeivan.core.datastore_user.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ioffeivan.core.datastore_user.UserLocalSerializer
import com.ioffeivan.core.datastore_user.model.UserLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val USER_DATASTORE_NAME = "user_datastore.json"

private val Context.userDataStore by dataStore(
    fileName = USER_DATASTORE_NAME,
    serializer = UserLocalSerializer,
)

@Module
@InstallIn(SingletonComponent::class)
internal class UserDataStoreModule {
    @Provides
    @Singleton
    fun provideUserDataStore(
        @ApplicationContext context: Context,
    ): DataStore<UserLocal> = context.userDataStore
}
