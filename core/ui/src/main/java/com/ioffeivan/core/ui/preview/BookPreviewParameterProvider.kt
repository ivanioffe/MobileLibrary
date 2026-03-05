package com.ioffeivan.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ioffeivan.core.model.Book

class BookPreviewParameterProvider : PreviewParameterProvider<Book> {
    override val values: Sequence<Book>
        get() =
            sequenceOf(
                Book(
                    id = "1",
                    title = "Название книги",
                    authors = "Автор И.О, Автор И.О",
                    thumbnailUrl = "url",
                ),
            )
}
