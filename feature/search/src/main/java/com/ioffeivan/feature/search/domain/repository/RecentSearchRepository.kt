package com.ioffeivan.feature.search.domain.repository

import kotlinx.coroutines.flow.Flow

internal interface RecentSearchRepository {
    fun observeRecentSearches(limit: Int): Flow<List<String>>

    suspend fun saveRecentSearch(query: String)

    suspend fun deleteRecentSearch(query: String)

    suspend fun clearRecentSearches()
}
