package com.ioffeivan.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class BooksDto(
    val items: List<BookDto>? = null,
)
