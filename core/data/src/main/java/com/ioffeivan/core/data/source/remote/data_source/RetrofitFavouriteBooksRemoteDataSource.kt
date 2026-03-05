package com.ioffeivan.core.data.source.remote.data_source

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.network.api.FavouriteBooksApiService
import com.ioffeivan.core.network.model.BooksDto
import javax.inject.Inject

internal class RetrofitFavouriteBooksRemoteDataSource @Inject constructor(
    private val favouriteBooksApiService: FavouriteBooksApiService,
) : FavouriteBooksRemoteDataSource {
    override suspend fun getFavouriteBooks(): DataResult<BooksDto, AppError> {
        return favouriteBooksApiService.getFavouriteBooks()
    }

    override suspend fun addBookToFavourites(bookId: String): DataResult<Unit, AppError> {
        return favouriteBooksApiService.addBookToFavourites(bookId)
    }

    override suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError> {
        return favouriteBooksApiService.removeBookFromFavourites(bookId)
    }
}
