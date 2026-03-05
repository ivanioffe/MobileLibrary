package com.ioffeivan.core.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val thumbnailUrl: String?,
    val isFavourite: Boolean = false,
)
