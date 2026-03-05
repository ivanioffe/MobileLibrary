package com.ioffeivan.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ioffeivan.core.database.dao.RecentSearchDao
import com.ioffeivan.core.database.model.RecentSearchEntity

@Database(
    entities = [
        RecentSearchEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}
