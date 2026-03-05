package com.ioffeivan.feature.book_details.data.source.remote.api

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.feature.book_details.data.source.remote.model.BookDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val FIELDS =
    "id,volumeInfo/title,volumeInfo/authors,volumeInfo/description,volumeInfo/imageLinks,userInfo/updated"

internal interface BookDetailsApiService {
    @GET("v1/volumes/{volumeId}")
    suspend fun getBookDetails(
        @Path("volumeId") bookId: String,
        @Query("fields") fields: String = FIELDS,
    ): DataResult<BookDetailsDto, AppError>
}
