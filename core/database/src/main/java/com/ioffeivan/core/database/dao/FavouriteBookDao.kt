package com.ioffeivan.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ioffeivan.core.database.model.FavouriteBookEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteBookDao {
    @Query(
        value = """
            SELECT * FROM favourite_books
        """,
    )
    fun observeFavouriteBooks(): Flow<List<FavouriteBookEntity>>

    @Transaction
    suspend fun refreshFavouriteBooks(favouriteBooks: List<FavouriteBookEntity>) {
        deleteAllFavouriteBooks()
        insertAllFavouriteBooks(favouriteBooks)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFavouriteBooks(favouriteBooks: List<FavouriteBookEntity>)

    @Query(
        value = """
            DELETE FROM favourite_books
        """,
    )
    suspend fun deleteAllFavouriteBooks()

    @Upsert
    suspend fun upsertFavouriteBook(favouriteBookEntity: FavouriteBookEntity)

    @Query(
        value = """
            DELETE FROM favourite_books
            WHERE id = :bookId
        """,
    )
    suspend fun deleteFavouriteBook(bookId: String)
}
