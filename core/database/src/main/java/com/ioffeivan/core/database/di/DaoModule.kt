package com.ioffeivan.core.database.di

import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.dao.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun provideRecentSearchDao(
        database: AppDatabase,
    ): RecentSearchDao = database.recentSearchDao()
}
