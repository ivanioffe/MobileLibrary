package com.ioffeivan.feature.book_details.data.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.feature.book_details.data.mapper.toBookDetails
import com.ioffeivan.feature.book_details.data.source.remote.data_source.BookDetailsRemoteDataSource
import com.ioffeivan.feature.book_details.domain.model.BookDetails
import com.ioffeivan.feature.book_details.domain.repository.BookDetailsRepository
import javax.inject.Inject

internal class BookDetailsRepositoryImpl @Inject constructor(
    private val bookDetailsRemoteDataSource: BookDetailsRemoteDataSource,
) : BookDetailsRepository {
    override suspend fun getBookDetails(bookId: String): DataResult<BookDetails, AppError> {
        val result = bookDetailsRemoteDataSource.getBookDetails(bookId)

        return when (result) {
            is DataResult.Success -> {
                DataResult.Success(result.data.toBookDetails())
            }

            is DataResult.Error -> {
                DataResult.Error(result.error)
            }
        }
    }
}
