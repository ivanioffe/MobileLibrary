package com.ioffeivan.core.domain.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.model.Books

interface FavouriteBooksRepository {
    suspend fun getFavouriteBooks(): DataResult<Books, AppError>

    suspend fun addBookToFavourites(bookId: String): DataResult<Unit, AppError>

    suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError>
}
