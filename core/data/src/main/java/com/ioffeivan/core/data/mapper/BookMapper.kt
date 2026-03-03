package com.ioffeivan.core.data.mapper

import com.ioffeivan.core.model.Book
import com.ioffeivan.core.model.Books
import com.ioffeivan.core.network.model.BookDto
import com.ioffeivan.core.network.model.BooksDto

fun BooksDto.toBooks(): Books {
    return Books(
        items = items?.map(BookDto::toBook) ?: listOf(),
    )
}

fun BookDto.toBook(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors,
        thumbnailUrl = thumbnailUrl,
    )
}
