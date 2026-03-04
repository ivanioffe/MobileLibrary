package com.ioffeivan.feature.search.data.source.local.di

import com.ioffeivan.feature.search.data.source.local.data_source.RecentSearchLocalDataSource
import com.ioffeivan.feature.search.data.source.local.data_source.RoomRecentSearchLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal interface RecentSearchLocalModule {
    @Binds
    fun bindRecentSearchLocalDataSource(
        impl: RoomRecentSearchLocalDataSource,
    ): RecentSearchLocalDataSource
}
