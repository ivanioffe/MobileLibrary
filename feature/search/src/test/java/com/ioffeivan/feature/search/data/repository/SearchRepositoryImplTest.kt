package com.ioffeivan.feature.search.data.repository

import com.google.common.truth.Truth.assertThat
import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.error.network.NetworkStatusCode
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.data.mapper.toBooks
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.feature.search.data.source.remote.data_source.SearchRemoteDataSource
import com.ioffeivan.feature.search.domain.model.SearchParams
import com.ioffeivan.feature.search.domain.repository.SearchRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchRepositoryImplTest {
    private lateinit var searchRemoteDataSource: SearchRemoteDataSource
    private lateinit var repository: SearchRepository

    private val searchParams = SearchParams(query = "query")

    @BeforeEach
    fun setUp() {
        searchRemoteDataSource = mockk()
        repository = SearchRepositoryImpl(searchRemoteDataSource)
    }

    @AfterEach
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun search_whenDataSourceReturnsSuccess_shouldReturnMappedBooks() =
        runTest {
            val booksDto = BooksDto(items = listOf(mockk(relaxed = true)))
            coEvery {
                searchRemoteDataSource.search(searchParams)
            } returns DataResult.Success(booksDto)

            val result = repository.search(searchParams)

            assertThat(result).isEqualTo(DataResult.Success(booksDto.toBooks()))
            coVerify { searchRemoteDataSource.search(searchParams) }
        }

    @Test
    fun search_whenDataSourceReturnsError_shouldReturnSameError() =
        runTest {
            val networkError = AppError.NetworkError(NetworkStatusCode.InternalServerError)
            val expected = DataResult.Error(networkError)
            coEvery { searchRemoteDataSource.search(searchParams) } returns expected

            val result = repository.search(searchParams)

            assertThat(result).isEqualTo(expected)
        }
}
