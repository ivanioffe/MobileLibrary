package com.ioffeivan.core.network.api

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

private const val FIELDS =
    "items(id,volumeInfo/title,volumeInfo/authors,volumeInfo/imageLinks,userInfo/updated)"

interface FavouriteBooksApiService {
    @GET("v1/mylibrary/bookshelves/0/volumes")
    suspend fun getFavouriteBooks(
        @Query("fields") fields: String = FIELDS,
    ): DataResult<BooksDto, AppError>

    @POST("v1/mylibrary/bookshelves/0/addVolume")
    suspend fun addBookToFavourites(
        @Query("volumeId") bookId: String,
    ): DataResult<Unit, AppError>

    @POST("v1/mylibrary/bookshelves/0/removeVolume")
    suspend fun removeBookFromFavourites(
        @Query("volumeId") bookId: String,
    ): DataResult<Unit, AppError>

    @GET("v1/volumes/{volumeId}")
    suspend fun getBookDetails(
        @Path("volumeId") bookId: String,
        @Query(
            "fields",
        ) fields: String = "id,volumeInfo/title,volumeInfo/authors,volumeInfo/description,volumeInfo/imageLinks,userInfo/updated",
    ): DataResult<Unit, AppError>
}
