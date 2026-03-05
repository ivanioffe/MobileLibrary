package com.ioffeivan.core.data.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.data.mapper.toBooks
import com.ioffeivan.core.data.source.remote.data_source.FavouriteBooksRemoteDataSource
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import com.ioffeivan.core.model.Books
import javax.inject.Inject

internal class FavouriteBooksRepositoryImpl @Inject constructor(
    private val favouriteBooksRemoteDataSource: FavouriteBooksRemoteDataSource,
) : FavouriteBooksRepository {
    override suspend fun getFavouriteBooks(): DataResult<Books, AppError> {
        val result = favouriteBooksRemoteDataSource.getFavouriteBooks()

        return when (result) {
            is DataResult.Success -> {
                DataResult.Success(result.data.toBooks())
            }

            is DataResult.Error -> {
                DataResult.Error(result.error)
            }
        }
    }

    override suspend fun addBookToFavourites(bookId: String): DataResult<Unit, AppError> {
        return favouriteBooksRemoteDataSource.addBookToFavourites(bookId)
    }

    override suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError> {
        return favouriteBooksRemoteDataSource.removeBookFromFavourites(bookId)
    }
}
