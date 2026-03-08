package com.ioffeivan.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_books")
data class FavouriteBookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val authors: String?,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String?,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean,
)
