package com.ioffeivan.core.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ioffeivan.core.model.Book

fun LazyGridScope.books(
    books: List<Book>,
    onBookClick: (String) -> Unit,
) {
    items(
        items = books,
        key = { it.id },
    ) { book ->
        BookItem(
            book = book,
            onClick = onBookClick,
            modifier =
                Modifier
                    .padding(
                        horizontal = 8.dp,
                        vertical = 8.dp,
                    ),
        )
    }
}
