package com.ioffeivan.feature.search.data.source.remote.api

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import retrofit2.http.GET
import retrofit2.http.Query

internal const val FIELDS = "items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/imageLinks)"

internal interface SearchApiService {
    @GET("v1/volumes")
    suspend fun search(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int = 30,
        @Query("fields") fields: String = FIELDS,
    ): DataResult<BooksDto, AppError>
}
