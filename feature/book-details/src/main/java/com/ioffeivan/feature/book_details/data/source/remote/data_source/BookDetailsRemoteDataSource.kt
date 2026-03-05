package com.ioffeivan.feature.book_details.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.feature.book_details.data.source.remote.model.BookDetailsDto

internal interface BookDetailsRemoteDataSource {
    suspend fun getBookDetails(bookId: String): DataResult<BookDetailsDto, AppError>
}
