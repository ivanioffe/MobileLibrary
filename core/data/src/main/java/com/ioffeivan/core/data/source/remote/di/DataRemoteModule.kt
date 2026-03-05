package com.ioffeivan.core.data.source.remote.di

import com.ioffeivan.core.data.source.remote.data_source.FavouriteBooksRemoteDataSource
import com.ioffeivan.core.data.source.remote.data_source.RetrofitFavouriteBooksRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataRemoteModule {
    @Binds
    fun bindFavouriteBooksRemoteDataSource(
        impl: RetrofitFavouriteBooksRemoteDataSource,
    ): FavouriteBooksRemoteDataSource
}
