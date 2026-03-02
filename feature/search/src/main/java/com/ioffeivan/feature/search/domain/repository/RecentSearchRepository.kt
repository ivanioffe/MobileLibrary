package com.ioffeivan.feature.search.domain.repository

import com.ioffeivan.feature.search.domain.model.SearchRecent
import kotlinx.coroutines.flow.Flow

internal interface RecentSearchRepository {
    fun observeRecentSearches(limit: Int): Flow<List<SearchRecent>>

    suspend fun addRecentSearch(query: String)

    suspend fun removeRecentSearch(query: String)

    suspend fun clearRecentSearches()
}
