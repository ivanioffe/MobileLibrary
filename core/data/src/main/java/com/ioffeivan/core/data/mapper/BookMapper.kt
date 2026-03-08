package com.ioffeivan.core.data.mapper

import com.ioffeivan.core.database.model.FavouriteBookEntity
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
        title = bookInfoDto.title,
        authors = bookInfoDto.authors?.joinToString(),
        thumbnailUrl = getThumbnail(bookInfoDto.imageLinks),
        isFavourite = userInfoDto != null,
    )
}

fun BookDto.toFavouriteBookEntity(): FavouriteBookEntity {
    return FavouriteBookEntity(
        id = id,
        title = bookInfoDto.title,
        authors = bookInfoDto.authors?.joinToString(),
        thumbnailUrl = getThumbnail(bookInfoDto.imageLinks),
        isFavourite = userInfoDto != null,
    )
}

fun FavouriteBookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite,
    )
}

fun Book.toFavouriteBookEntity(): FavouriteBookEntity {
    return FavouriteBookEntity(
        id = id,
        title = title,
        authors = authors,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite,
    )
}

private fun getThumbnail(imageLinks: ImageLinks?): String? {
    return (imageLinks?.thumbnail ?: imageLinks?.smallThumbnail)
        ?.replace("http", "https")
}
