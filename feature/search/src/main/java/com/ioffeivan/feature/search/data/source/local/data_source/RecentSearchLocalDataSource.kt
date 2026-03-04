package com.ioffeivan.feature.search.data.source.local.data_source

import com.ioffeivan.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

internal interface RecentSearchLocalDataSource {
    fun observeRecentSearches(limit: Int): Flow<List<RecentSearchEntity>>

    suspend fun upsertRecentSearch(recentSearchEntity: RecentSearchEntity)

    suspend fun deleteRecentSearch(query: String)

    suspend fun deleteAllRecentSearch()
}
