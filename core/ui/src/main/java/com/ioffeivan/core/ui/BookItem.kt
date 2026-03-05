package com.ioffeivan.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.designsystem.theme.Grey600
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.ui.preview.BookPreviewParameterProvider

@Composable
fun BookItem(
    book: Book,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .clickable(
                    onClick =
                        onDebounceClick {
                            onClick(book.id)
                        },
                ),
    ) {
        AsyncImage(
            model = book.thumbnailUrl,
            contentDescription = null,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier =
                Modifier
                    .padding(4.dp),
        ) {
            Text(
                text = book.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    book.authors?.joinToString(", ")
                        ?: stringResource(R.string.author_not_specified),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style =
                    MaterialTheme.typography.bodySmall.copy(
                        color = Grey600,
                    ),
            )
        }
    }
}

@Preview
@Composable
private fun BookItemPreview(
    @PreviewParameter(BookPreviewParameterProvider::class)
    book: Book,
) {
    PreviewContainer {
        BookItem(
            book = book,
            onClick = {},
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun GridBookItemPreview(
    @PreviewParameter(BookPreviewParameterProvider::class)
    book: Book,
) {
    PreviewContainer {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            item {
                BookItem(
                    book = book,
                    onClick = {},
                )
            }

            item {
                BookItem(
                    book = book,
                    onClick = {},
                )
            }
        }
    }
}
