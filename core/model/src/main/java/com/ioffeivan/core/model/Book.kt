package com.ioffeivan.core.model

data class Book(
    val id: String,
    val title: String,
    val authors: String?,
    val thumbnailUrl: String?,
    val isFavourite: Boolean = false,
)
