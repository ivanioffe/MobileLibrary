package com.ioffeivan.feature.search.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.common.CommonStatusCode
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.domain.base.Result
import com.ioffeivan.core.model.Books
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchUseCaseTest {
    private lateinit var searchRepository: SearchRepository
    private lateinit var searchUseCase: SearchUseCase

    private val testDispatcher = UnconfinedTestDispatcher()
    private val searchParams = SearchParams(query = "query")

    @BeforeEach
    fun setUp() {
        searchRepository = mockk()
        searchUseCase = SearchUseCase(searchRepository, testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun whenRepositoryReturnsSuccessWithBooks_shouldReturnSuccessWithSearchData() =
        runTest {
            val books = Books(items = listOf(mockk()))
            coEvery { searchRepository.search(searchParams) } returns DataResult.Success(books)

            val result = searchUseCase(searchParams)

            assertThat(result)
                .isEqualTo(Result.Success(SearchUseCase.SearchSuccess.SearchData(books)))
            coVerify(exactly = 1) { searchRepository.search(searchParams) }
        }

    @Test
    fun whenRepositoryReturnsSuccessWithEmptyBooks_shouldReturnBusinessRuleErrorNoSearchData() =
        runTest {
            val emptyBooks = Books(items = emptyList())
            coEvery { searchRepository.search(searchParams) } returns DataResult.Success(emptyBooks)

            val result = searchUseCase(searchParams)

            assertThat(result)
                .isEqualTo(Result.BusinessRuleError(SearchUseCase.SearchErrors.NoSearchData))
            coVerify(exactly = 1) { searchRepository.search(searchParams) }
        }

    @Test
    fun whenRepositoryReturnsError_shouldReturnError() =
        runTest {
            val error = AppError.CommonError(CommonStatusCode.Unknown)
            coEvery { searchRepository.search(searchParams) } returns DataResult.Error(error)

            val result = searchUseCase(searchParams)

            assertThat(result).isEqualTo(Result.Error(error))
            coVerify(exactly = 1) { searchRepository.search(searchParams) }
        }
}
