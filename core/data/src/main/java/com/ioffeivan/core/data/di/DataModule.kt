package com.ioffeivan.core.data.di

import com.ioffeivan.core.data.repository.FavouriteBooksRepositoryImpl
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindFavouriteBooksRepository(impl: FavouriteBooksRepositoryImpl): FavouriteBooksRepository
}
