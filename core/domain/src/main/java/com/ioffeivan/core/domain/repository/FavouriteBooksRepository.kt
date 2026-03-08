package com.ioffeivan.core.domain.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.model.Book
import kotlinx.coroutines.flow.Flow

interface FavouriteBooksRepository {
    fun observeFavouriteBooks(): Flow<List<Book>>

    suspend fun refreshFavouriteBooks(): DataResult<Unit, AppError>

    suspend fun addBookToFavourites(book: Book): DataResult<Unit, AppError>

    suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError>
}
