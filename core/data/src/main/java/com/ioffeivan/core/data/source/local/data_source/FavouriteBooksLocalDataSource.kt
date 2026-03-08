package com.ioffeivan.core.data.source.local.data_source

import com.ioffeivan.core.database.model.FavouriteBookEntity
import kotlinx.coroutines.flow.Flow

internal interface FavouriteBooksLocalDataSource {
    fun observeFavouriteBooks(): Flow<List<FavouriteBookEntity>>

    suspend fun refreshFavouriteBooks(favouriteBookEntities: List<FavouriteBookEntity>)

    suspend fun upsertFavouriteBook(favouriteBookEntity: FavouriteBookEntity)

    suspend fun deleteFavouriteBook(bookId: String)
}
