package com.ioffeivan.feature.sign_in.data.source.local.di

import com.ioffeivan.feature.sign_in.data.source.local.data_source.DataStoreSignInLocalDataSource
import com.ioffeivan.feature.sign_in.data.source.local.data_source.DataStoreUserLocalDataSource
import com.ioffeivan.feature.sign_in.data.source.local.data_source.SignInLocalDataSource
import com.ioffeivan.feature.sign_in.data.source.local.data_source.UserLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface SignInLocalModuleBinder {
    @Binds
    fun bindSignInLocalDataSource(
        impl: DataStoreSignInLocalDataSource,
    ): SignInLocalDataSource

    @Binds
    fun bindUserLocalDataSource(
        impl: DataStoreUserLocalDataSource,
    ): UserLocalDataSource
}
