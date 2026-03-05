package com.ioffeivan.feature.book_details.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.book_details.data.source.remote.api.BookDetailsApiService
import com.ioffeivan.feature.book_details.data.source.remote.data_source.BookDetailsRemoteDataSource
import com.ioffeivan.feature.book_details.data.source.remote.data_source.RetrofitBookDetailsRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface BookDetailsRemoteModule {
    @Binds
    fun bindBookDetailsRemoteDataSource(
        impl: RetrofitBookDetailsRemoteDataSource,
    ): BookDetailsRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideBookDetailsApiService(
            @Authorized retrofit: Retrofit,
        ): BookDetailsApiService {
            return retrofit.create()
        }
    }
}
