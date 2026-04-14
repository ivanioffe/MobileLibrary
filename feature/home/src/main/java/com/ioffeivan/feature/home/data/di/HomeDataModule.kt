package com.ioffeivan.feature.home.data.di

import com.ioffeivan.feature.home.data.repository.HomeRepositoryImpl
import com.ioffeivan.feature.home.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface HomeDataModule {
    @Binds
    fun bindHomeRepository(impl: HomeRepositoryImpl): HomeRepository
}
