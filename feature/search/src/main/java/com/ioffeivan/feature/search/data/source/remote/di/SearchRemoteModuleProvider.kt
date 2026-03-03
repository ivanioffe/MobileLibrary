package com.ioffeivan.feature.search.data.source.remote.di

import com.ioffeivan.core.network.di.Authorized
import com.ioffeivan.feature.search.data.source.remote.api.SearchApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal class SearchRemoteModuleProvider {
    @Provides
    @Singleton
    fun provideSearchApiService(
        @Authorized retrofit: Retrofit,
    ): SearchApiService {
        return retrofit.create()
    }
}
