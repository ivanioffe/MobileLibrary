package com.ioffeivan.feature.book_details.domain.model

internal data class BookDetails(
    val id: String,
    val title: String,
    val authors: String?,
    val description: String?,
    val imageUrl: String?,
    val isFavourite: Boolean,
) {
    companion object {
        fun initial(): BookDetails {
            return BookDetails(
                id = "1",
                title = "",
                authors = null,
                description = null,
                imageUrl = null,
                isFavourite = false,
            )
        }
    }
}
