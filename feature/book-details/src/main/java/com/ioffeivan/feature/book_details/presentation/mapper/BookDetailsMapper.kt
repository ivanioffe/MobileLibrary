package com.ioffeivan.feature.book_details.presentation.mapper

import com.ioffeivan.core.model.Book
import com.ioffeivan.feature.book_details.domain.model.BookDetails

internal fun BookDetails.toBook(): Book {
    return Book(
        id = id,
        title = title,
        authors = authors,
        thumbnailUrl = thumbnailUrl,
        isFavourite = isFavourite,
    )
}
