package com.ioffeivan.core.data.repository

import com.ioffeivan.core.common.error.AppError
import com.ioffeivan.core.common.result.DataResult
import com.ioffeivan.core.common.result.onSuccess
import com.ioffeivan.core.data.mapper.toBook
import com.ioffeivan.core.data.mapper.toFavouriteBookEntity
import com.ioffeivan.core.data.source.local.data_source.FavouriteBooksLocalDataSource
import com.ioffeivan.core.data.source.remote.data_source.FavouriteBooksRemoteDataSource
import com.ioffeivan.core.database.model.FavouriteBookEntity
import com.ioffeivan.core.domain.repository.FavouriteBooksRepository
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.network.model.BookDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FavouriteBooksRepositoryImpl @Inject constructor(
    private val favouriteBooksRemoteDataSource: FavouriteBooksRemoteDataSource,
    private val favouriteBooksLocalDataSource: FavouriteBooksLocalDataSource,
) : FavouriteBooksRepository {
    override fun observeFavouriteBooks(): Flow<List<Book>> {
        return favouriteBooksLocalDataSource.observeFavouriteBooks()
            .map { favouriteBookEntities ->
                favouriteBookEntities.map(FavouriteBookEntity::toBook)
            }
    }

    override suspend fun refreshFavouriteBooks(): DataResult<Unit, AppError> {
        val result = favouriteBooksRemoteDataSource.getFavouriteBooks()

        return when (result) {
            is DataResult.Success -> {
                result.data.items
                    ?.map(BookDto::toFavouriteBookEntity)
                    .let {
                        favouriteBooksLocalDataSource.refreshFavouriteBooks(it ?: listOf())
                    }

                DataResult.Success(Unit)
            }

            is DataResult.Error -> {
                DataResult.Error(result.error)
            }
        }
    }

    override suspend fun addBookToFavourites(book: Book): DataResult<Unit, AppError> {
        return favouriteBooksRemoteDataSource.addBookToFavourites(book.id)
            .onSuccess {
                favouriteBooksLocalDataSource.upsertFavouriteBook(
                    book.toFavouriteBookEntity(),
                )
            }
    }

    override suspend fun removeBookFromFavourites(bookId: String): DataResult<Unit, AppError> {
        return favouriteBooksRemoteDataSource.removeBookFromFavourites(bookId)
            .onSuccess {
                favouriteBooksLocalDataSource.deleteFavouriteBook(bookId)
            }
    }
}
