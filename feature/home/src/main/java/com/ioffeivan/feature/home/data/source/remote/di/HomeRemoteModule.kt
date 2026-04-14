package com.ioffeivan.feature.home.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.home.data.source.remote.api.HomeApiService
import com.ioffeivan.feature.home.data.source.remote.data_source.HomeRemoteDataSource
import com.ioffeivan.feature.home.data.source.remote.data_source.RetrofitHomeRemoteDataSource
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
internal interface HomeRemoteModule {
    @Binds
    fun bindHomeRemoteDataSource(impl: RetrofitHomeRemoteDataSource): HomeRemoteDataSource

    companion object {
        @Provides
        @Singleton
        fun provideHomeApiService(
            @Authorized retrofit: Retrofit,
        ): HomeApiService {
            return retrofit.create()
        }
    }
}
