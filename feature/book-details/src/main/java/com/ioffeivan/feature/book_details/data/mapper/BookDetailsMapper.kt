package com.ioffeivan.feature.book_details.data.mapper

import com.ioffeivan.feature.book_details.data.source.remote.model.BookDetailsDto
import com.ioffeivan.feature.book_details.data.source.remote.model.BookDetailsImageLinks
import com.ioffeivan.feature.book_details.domain.model.BookDetails

internal fun BookDetailsDto.toBookDetails(): BookDetails {
    return BookDetails(
        id = id,
        title = bookDetailsInfoDto.title,
        authors = bookDetailsInfoDto.authors?.joinToString(),
        description = bookDetailsInfoDto.description,
        imageUrl = getImage(bookDetailsInfoDto.imageLinks),
        isFavourite = userInfoDto != null,
    )
}

private fun getImage(imageLinks: BookDetailsImageLinks?): String? {
    return (imageLinks?.extraLarge ?: imageLinks?.large ?: imageLinks?.medium)
        ?.replace("http", "https")
}
