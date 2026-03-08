package com.ioffeivan.core.data.source.local.di

import com.ioffeivan.core.data.source.local.data_source.FavouriteBooksLocalDataSource
import com.ioffeivan.core.data.source.local.data_source.RoomFavouriteBooksLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataLocalModule {
    @Binds
    fun bindFavouriteBooksLocalDataSource(
        impl: RoomFavouriteBooksLocalDataSource,
    ): FavouriteBooksLocalDataSource
}
