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
    private lateinit var useCase: AddRecentSearchUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private val query = "query"

    @BeforeEach
    fun setUp() {
        recentSearchRepository = mockk()
        useCase = AddRecentSearchUseCase(recentSearchRepository, testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenCalled_shouldReturnSuccessUnit() =
        runTest {
            coEvery { recentSearchRepository.addRecentSearch(query) } returns Unit

            val result = useCase(query)

            assertThat(result).isEqualTo(Result.Success(Unit))
            coVerify(exactly = 1) { recentSearchRepository.addRecentSearch(query) }
        }

    @Test
    fun whenRepositoryThrowsException_shouldReturnError() =
        runTest {
            coEvery { recentSearchRepository.addRecentSearch(query) } throws RuntimeException()

            val result = useCase(query)

            assertThat(result)
                .isEqualTo(Result.Error(AppError.CommonError(CommonStatusCode.Unknown)))
            coVerify(exactly = 1) { recentSearchRepository.addRecentSearch(query) }
        }
}
