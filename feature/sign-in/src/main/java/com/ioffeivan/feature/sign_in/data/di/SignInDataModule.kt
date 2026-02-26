package com.ioffeivan.feature.sign_in.data.di

import com.ioffeivan.feature.sign_in.data.repository.SignInRepositoryImpl
import com.ioffeivan.feature.sign_in.domain.repository.SignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface SignInDataModule {
    @Binds
    fun bindSignInRepository(
        impl: SignInRepositoryImpl,
    ): SignInRepository
}
