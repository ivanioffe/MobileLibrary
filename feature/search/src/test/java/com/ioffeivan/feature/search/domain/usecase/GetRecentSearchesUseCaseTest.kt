package com.ioffeivan.feature.search.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetRecentSearchesUseCaseTest {
    private lateinit var recentSearchRepository: RecentSearchRepository
    private lateinit var useCase: ObserveRecentSearchesUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private val limit = 5

    @BeforeEach
    fun setUp() {
        recentSearchRepository = mockk()
        useCase = ObserveRecentSearchesUseCase(recentSearchRepository, testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenRepositoryReturnsRecentSearches_shouldReturnSuccessFlow() =
        runTest {
            val recentSearches = listOf("query1", "query2")
            coEvery {
                recentSearchRepository.observeRecentSearches(limit)
            } returns flowOf(recentSearches)

            val flow = useCase(limit)

            flow.test {
                val result = awaitItem()

                assertThat(result)
                    .isEqualTo(
                        Result.Success(
                            ObserveRecentSearchesUseCase.ObserveRecentSearchesSuccess.RecentSearches(
                                recentSearches,
                            ),
                        ),
                    )

                awaitComplete()
            }
        }
}
