package com.ioffeivan.core.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.model.Books

class BooksPreviewParameterProvider : PreviewParameterProvider<Books> {
    override val values: Sequence<Books>
        get() =
            sequenceOf(
                Books(
                    listOf(
                        Book(
                            id = "1",
                            title = "Название книги",
                            authors = "Автор И.О",
                            thumbnailUrl = "url",
                        ),
                        Book(
                            id = "2",
                            title = "Длинное название книги",
                            authors = "Автор И.О",
                            thumbnailUrl = "url",
                        ),
                        Book(
                            id = "3",
                            title = "Бумажная книга",
                            authors = "Автор Автор",
                            thumbnailUrl = "url",
                        ),
                    ),
                ),
            )
}
