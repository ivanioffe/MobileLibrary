package com.ioffeivan.core.data.source.local.data_source

import com.ioffeivan.core.database.dao.FavouriteBookDao
import com.ioffeivan.core.database.model.FavouriteBookEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class RoomFavouriteBooksLocalDataSource @Inject constructor(
    private val favouriteBookDao: FavouriteBookDao,
) : FavouriteBooksLocalDataSource {
    override fun observeFavouriteBooks(): Flow<List<FavouriteBookEntity>> {
        return favouriteBookDao.observeFavouriteBooks()
    }

    override suspend fun refreshFavouriteBooks(favouriteBookEntities: List<FavouriteBookEntity>) {
        favouriteBookDao.refreshFavouriteBooks(favouriteBookEntities)
    }

    override suspend fun upsertFavouriteBook(favouriteBookEntity: FavouriteBookEntity) {
        favouriteBookDao.upsertFavouriteBook(favouriteBookEntity)
    }

    override suspend fun deleteFavouriteBook(bookId: String) {
        favouriteBookDao.deleteFavouriteBook(bookId)
    }
}
