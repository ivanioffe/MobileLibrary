package com.ioffeivan.feature.search.data.repository

import com.ioffeivan.core.database.model.RecentSearchEntity
import com.ioffeivan.feature.search.data.source.local.data_source.RecentSearchLocalDataSource
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RecentSearchRepositoryImpl @Inject constructor(
    private val recentSearchLocalDataSource: RecentSearchLocalDataSource,
) : RecentSearchRepository {
    override fun observeRecentSearches(limit: Int): Flow<List<String>> {
        return recentSearchLocalDataSource.observeRecentSearches(limit)
            .map { recentSearches -> recentSearches.map { it.query } }
    }

    override suspend fun saveRecentSearch(query: String) {
        recentSearchLocalDataSource.upsertRecentSearch(
            RecentSearchEntity(
                query = query,
                timestamp = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun deleteRecentSearch(query: String) {
        recentSearchLocalDataSource.deleteRecentSearch(query)
    }

    override suspend fun clearRecentSearches() {
        recentSearchLocalDataSource.deleteAllRecentSearch()
    }
}
