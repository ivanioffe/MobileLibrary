package com.ioffeivan.feature.book_details.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.feature.book_details.data.source.remote.api.BookDetailsApiService
import com.ioffeivan.feature.book_details.data.source.remote.model.BookDetailsDto
import javax.inject.Inject

internal class RetrofitBookDetailsRemoteDataSource @Inject constructor(
    private val bookDetailsApiService: BookDetailsApiService,
) : BookDetailsRemoteDataSource {
    override suspend fun getBookDetails(bookId: String): DataResult<BookDetailsDto, AppError> {
        return bookDetailsApiService.getBookDetails(bookId)
    }
}
