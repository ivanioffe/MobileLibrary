package com.ioffeivan.core.data.mapper

import com.ioffeivan.core.model.Book
import com.ioffeivan.core.model.Books
import com.ioffeivan.core.network.model.BookDto
import com.ioffeivan.core.network.model.BooksDto
import com.ioffeivan.core.network.model.ImageLinks

fun BooksDto.toBooks(): Books {
    return Books(
        items = items?.map(BookDto::toBook) ?: listOf(),
    )
}

fun BookDto.toBook(): Book {
    return Book(
        id = id,
        title = bookInfo.title,
        authors = bookInfo.authors,
        thumbnailUrl = getThumbnail(bookInfo.imageLinks),
    )
}

private fun getThumbnail(imageLinks: ImageLinks?): String? {
    return (imageLinks?.thumbnail ?: imageLinks?.smallThumbnail)
        ?.replace("http", "https")
}
