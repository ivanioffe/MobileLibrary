package com.ioffeivan.feature.book_details.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ioffeivan.feature.book_details.domain.model.BookDetails

internal class BookDetailsPreviewParameterProvider : PreviewParameterProvider<BookDetails> {
    override val values: Sequence<BookDetails> =
        sequenceOf(
            BookDetails(
                id = "1",
                title = "Длинное название книги",
                authors = "Автор И.О, Автор И.О",
                description = "Описание описание описание. Описание описание описание описание описание описаниеописание описание.",
                thumbnailUrl = "url",
                imageUrl = "url",
                isFavourite = false,
            ),
        )
}
