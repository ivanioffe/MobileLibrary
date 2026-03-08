package com.ioffeivan.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ioffeivan.core.database.dao.FavouriteBookDao
import com.ioffeivan.core.database.dao.RecentSearchDao
import com.ioffeivan.core.database.model.FavouriteBookEntity
import com.ioffeivan.core.database.model.RecentSearchEntity

@Database(
    entities = [
        RecentSearchEntity::class,
        FavouriteBookEntity::class,
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao

    abstract fun favouriteBookDao(): FavouriteBookDao
}
