package com.ioffeivan.core.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.model.BooksDto

internal interface FavouriteBooksRemoteDataSource {
    suspend fun getFavouriteBooks(): DataResult<BooksDto, AppError>

    suspend fun addBookToFavourites(bookId: String): DataResult<Unit, AppError>

    suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError>
}
