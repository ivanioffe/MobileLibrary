package com.ioffeivan.feature.home.data.source.remote.api

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import retrofit2.http.GET
import retrofit2.http.Query

private const val FIELDS =
    "items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/imageLinks)"

internal interface HomeApiService {
    @GET("v1/mylibrary/bookshelves/8/volumes")
    suspend fun getRecommendedBooks(
        @Query("fields") fields: String = FIELDS,
    ): DataResult<BooksDto, AppError>
}
