package com.ioffeivan.feature.search.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.database.model.RecentSearchEntity
import com.ioffeivan.feature.search.data.source.local.data_source.RecentSearchLocalDataSource
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RecentSearchRepositoryImplTest {
    private lateinit var recentSearchLocalDataSource: RecentSearchLocalDataSource
    private lateinit var repository: RecentSearchRepository

    @BeforeEach
    fun setUp() {
        recentSearchLocalDataSource = mockk()
        repository = RecentSearchRepositoryImpl(recentSearchLocalDataSource)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun observeRecentSearches_whenDataSourceReturnsEntities_shouldReturnMappedStrings() =
        runTest {
            val entities =
                listOf(
                    RecentSearchEntity("query1", 1000L),
                    RecentSearchEntity("query2", 2000L),
                )
            val expectedStrings = listOf(entities[0].query, entities[1].query)
            every {
                recentSearchLocalDataSource.observeRecentSearches(any())
            } returns flowOf(entities)

            repository.observeRecentSearches(10).test {
                val result = awaitItem()
                assertThat(result).isEqualTo(expectedStrings)

                awaitComplete()
            }
            coVerify(exactly = 1) { recentSearchLocalDataSource.observeRecentSearches(any()) }
        }

    @Test
    fun saveRecentSearch_whenCalled_shouldMapToEntityWithCorrectQuery() =
        runTest {
            val query = "query"
            coEvery {
                recentSearchLocalDataSource.upsertRecentSearch(any())
            } just runs

            repository.saveRecentSearch(query)

            coVerify(exactly = 1) {
                recentSearchLocalDataSource.upsertRecentSearch(match { it.query == query })
            }
        }

    @Test
    fun deleteRecentSearch_whenCalled_shouldDelegateToDataSource() =
        runTest {
            val query = "query"
            coEvery { recentSearchLocalDataSource.deleteRecentSearch(query) } just runs

            repository.deleteRecentSearch(query)

            coVerify(exactly = 1) { recentSearchLocalDataSource.deleteRecentSearch(query) }
        }

    @Test
    fun clearRecentSearches_whenCalled_shouldDelegateToDataSource() =
        runTest {
            coEvery { recentSearchLocalDataSource.deleteAllRecentSearch() } just runs

            repository.clearRecentSearches()

            coVerify(exactly = 1) { recentSearchLocalDataSource.deleteAllRecentSearch() }
        }
}
