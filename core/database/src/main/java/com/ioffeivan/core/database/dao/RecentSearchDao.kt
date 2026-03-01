package com.ioffeivan.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ioffeivan.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query(
        value = """
            SELECT * FROM recent_searches
            ORDER BY timestamp DESC
            LIMIT :limit
        """,
    )
    fun observeRecentSearches(limit: Int): Flow<List<RecentSearchEntity>>

    @Upsert
    suspend fun upsertRecentSearch(recentSearchEntity: RecentSearchEntity)

    @Query(
        value = """
            DELETE FROM recent_searches
            WHERE `query` = :query
        """,
    )
    suspend fun deleteRecentSearch(query: String)

    @Query(
        value = """
            DELETE FROM recent_searches
        """,
    )
    suspend fun deleteAllRecentSearch()
}
