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
        thumbnailUrl = getThumbnail(bookDetailsInfoDto.imageLinks),
        isFavourite = userInfoDto != null,
    )
}

private fun getImage(imageLinks: BookDetailsImageLinks?): String? {
    return (imageLinks?.medium ?: imageLinks?.small)
        ?.replace("http", "https")
}

private fun getThumbnail(imageLinks: BookDetailsImageLinks?): String? {
    return (imageLinks?.thumbnail ?: imageLinks?.smallThumbnail)
        ?.replace("http", "https")
}
