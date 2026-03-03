package com.ioffeivan.feature.search.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.search.data.source.remote.api.SearchApiService
import com.ioffeivan.feature.search.data.source.remote.data_source.RetrofitSearchRemoteDataSource
import com.ioffeivan.feature.search.data.source.remote.data_source.SearchRemoteDataSource
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
internal interface SearchRemoteModule {
    @Binds
    fun bindSearchRemoteDataSource(
        impl: RetrofitSearchRemoteDataSource,
    ): SearchRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideSearchApiService(
            @Authorized retrofit: Retrofit,
        ): SearchApiService {
            return retrofit.create()
        }
    }
}
