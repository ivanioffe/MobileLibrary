package com.ioffeivan.core.datastore_auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.ioffeivan.core.datastore_auth.AuthDataLocalSerializer
import com.ioffeivan.core.datastore_auth.model.AuthDataLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val AUTH_DATASTORE_NAME = "auth_datastore.json"

private val Context.authDataStore by dataStore(
    fileName = AUTH_DATASTORE_NAME,
    serializer = AuthDataLocalSerializer,
)

@Module
@InstallIn(SingletonComponent::class)
internal class AuthDataStoreModule {
    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context: Context,
    ): DataStore<AuthDataLocal> = context.authDataStore
}
