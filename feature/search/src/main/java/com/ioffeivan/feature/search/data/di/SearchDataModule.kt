package com.ioffeivan.feature.search.data.di

import com.ioffeivan.feature.search.data.repository.SearchRepositoryImpl
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface SearchDataModule {
    @Binds
    fun bind(impl: SearchRepositoryImpl): SearchRepository
}
