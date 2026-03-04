package com.ioffeivan.feature.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.common.CommonStatusCode
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.feature.search.domain.repository.RecentSearchRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AddRecentSearchUseCaseTest {
    private lateinit var recentSearchRepository: RecentSearchRepository
    private lateinit var useCase: SaveRecentSearchUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private val query = "query"

    @BeforeEach
    fun setUp() {
        recentSearchRepository = mockk()
        useCase = SaveRecentSearchUseCase(recentSearchRepository, testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenCalled_shouldReturnSuccessUnit() =
        runTest {
            coEvery { recentSearchRepository.saveRecentSearch(query) } returns Unit

            val result = useCase(query)

            assertThat(result).isEqualTo(Result.Success(Unit))
            coVerify(exactly = 1) { recentSearchRepository.saveRecentSearch(query) }
        }

    @Test
    fun whenRepositoryThrowsException_shouldReturnError() =
        runTest {
            val exception = RuntimeException()
            coEvery { recentSearchRepository.saveRecentSearch(query) } throws exception

            val result = useCase(query)

            assertThat(result)
                .isEqualTo(Result.Error(AppError.CommonError(CommonStatusCode.Unknown, exception)))
            coVerify(exactly = 1) { recentSearchRepository.saveRecentSearch(query) }
        }
}
