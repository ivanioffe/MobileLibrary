package com.ioffeivan.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.database.AppDatabase
import com.ioffeivan.core.database.model.RecentSearchEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecentSearchDaoTest {
    lateinit var recentSearchDao: RecentSearchDao
    lateinit var appDatabase: AppDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase =
            Room.inMemoryDatabaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
            ).build()
        recentSearchDao = appDatabase.recentSearchDao()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun observeRecentSearches_whenNoData_shouldReturnEmptyList() =
        runTest {
            val flow = recentSearchDao.observeRecentSearches(5)
            val actual = flow.first()

            assertThat(actual).isEmpty()
        }

    @Test
    fun observeRecentSearches_whenDataExists_shouldReturnOrderedByTimestampDesc() =
        runTest {
            val entity1 = RecentSearchEntity("query1", 1000L)
            val entity2 = RecentSearchEntity("query2", 2000L)
            val entity3 = RecentSearchEntity("query3", 3000L)
            recentSearchDao.upsertRecentSearch(entity1)
            recentSearchDao.upsertRecentSearch(entity2)
            recentSearchDao.upsertRecentSearch(entity3)

            val actual = recentSearchDao.observeRecentSearches(10).first()

            assertThat(actual).isEqualTo(listOf(entity3, entity2, entity1))
        }

    @Test
    fun observeRecentSearches_whenDataExists_shouldReturnSortedListLimited() =
        runTest {
            val limit = 2
            val entity1 = RecentSearchEntity("query1", 1000L)
            val entity2 = RecentSearchEntity("query2", 2000L)
            val entity3 = RecentSearchEntity("query3", 3000L)
            recentSearchDao.upsertRecentSearch(entity1)
            recentSearchDao.upsertRecentSearch(entity2)
            recentSearchDao.upsertRecentSearch(entity3)

            val actual = recentSearchDao.observeRecentSearches(limit).first()

            assertThat(actual).hasSize(limit)
            assertThat(actual).isEqualTo(listOf(entity3, entity2))
        }

    @Test
    fun upsertRecentSearch_whenNewEntity_shouldInsert() =
        runTest {
            val entity = RecentSearchEntity("query", 1000L)

            recentSearchDao.upsertRecentSearch(entity)

            val actual = recentSearchDao.observeRecentSearches(30).first()
            assertThat(actual).hasSize(1)
            assertThat(actual).isEqualTo(listOf(entity))
        }

    @Test
    fun upsertRecentSearch_whenExistingEntity_shouldUpdate() =
        runTest {
            val query = "query"
            val entity = RecentSearchEntity(query, 1000L)
            recentSearchDao.upsertRecentSearch(entity)

            val updatedEntity = RecentSearchEntity(query, 2000L)
            recentSearchDao.upsertRecentSearch(updatedEntity)

            val actual = recentSearchDao.observeRecentSearches(1).first()
            assertThat(actual).hasSize(1)
            assertThat(actual).isEqualTo(listOf(updatedEntity))
        }

    @Test
    fun deleteRecentSearch_whenEntityExists_shouldRemoveIt() =
        runTest {
            val query = "query"
            val entity = RecentSearchEntity(query, 1000L)
            recentSearchDao.upsertRecentSearch(entity)

            recentSearchDao.deleteRecentSearch(query)

            val actual = recentSearchDao.observeRecentSearches(5).first()
            assertThat(actual).isEmpty()
        }

    @Test
    fun deleteRecentSearch_whenEntityDoesNotExist_shouldDoNothing() =
        runTest {
            val existingEntity = RecentSearchEntity("query", 1000L)
            recentSearchDao.upsertRecentSearch(existingEntity)

            recentSearchDao.deleteRecentSearch("noExistingQuery")

            val actual = recentSearchDao.observeRecentSearches(5).first()
            assertThat(actual).hasSize(1)
            assertThat(actual).isEqualTo(listOf(existingEntity))
        }

    @Test
    fun deleteAllRecentSearch_whenDataExists_shouldClearTable() =
        runTest {
            val entity1 = RecentSearchEntity("query1", 1000L)
            val entity2 = RecentSearchEntity("query2", 2000L)
            recentSearchDao.upsertRecentSearch(entity1)
            recentSearchDao.upsertRecentSearch(entity2)

            recentSearchDao.deleteAllRecentSearch()

            val actual = recentSearchDao.observeRecentSearches(30).first()
            assertThat(actual).isEmpty()
        }
}
