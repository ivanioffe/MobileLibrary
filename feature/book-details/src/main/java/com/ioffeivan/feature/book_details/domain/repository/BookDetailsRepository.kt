package com.ioffeivan.feature.book_details.domain.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.feature.book_details.domain.model.BookDetails

internal interface BookDetailsRepository {
    suspend fun getBookDetails(bookId: String): DataResult<BookDetails, AppError>
}
