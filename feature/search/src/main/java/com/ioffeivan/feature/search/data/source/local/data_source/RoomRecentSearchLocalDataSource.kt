package com.ioffeivan.feature.search.data.source.local.data_source

import com.ioffeivan.core.database.dao.RecentSearchDao
import com.ioffeivan.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RoomRecentSearchLocalDataSource @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
) : RecentSearchLocalDataSource {
    override fun observeRecentSearches(limit: Int): Flow<List<RecentSearchEntity>> {
        return recentSearchDao.observeRecentSearches(limit)
    }

    override suspend fun upsertRecentSearch(recentSearchEntity: RecentSearchEntity) {
        recentSearchDao.upsertRecentSearch(recentSearchEntity)
    }

    override suspend fun deleteRecentSearch(query: String) {
        recentSearchDao.deleteRecentSearch(query)
    }

    override suspend fun deleteAllRecentSearch() {
        recentSearchDao.deleteAllRecentSearch()
    }
}
