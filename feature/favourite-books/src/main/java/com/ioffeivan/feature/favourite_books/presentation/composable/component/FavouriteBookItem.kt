package com.ioffeivan.feature.favourite_books.presentation.composable.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.designsystem.theme.Grey600
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.core.ui.preview.BookPreviewParameterProvider

@Composable
internal fun FavouriteBookItem(
    book: Book,
    onClick: (String) -> Unit,
    onFavouriteClick: (String) -> Unit,
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
        Box {
            AsyncImage(
                model = book.thumbnailUrl,
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp),
            )

            IconButton(
                onClick =
                    onDebounceClick {
                        onFavouriteClick(book.id)
                    },
                modifier =
                    Modifier
                        .align(Alignment.TopEnd),
            ) {
                PrimaryIcon(
                    id = PrimaryIcons.FilledFavourite,
                    tint = Color.Red,
                )
            }
        }

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
                text = book.authors?.joinToString(", ") ?: "Автор не указан",
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
private fun FavouriteBookItemPreview(
    @PreviewParameter(BookPreviewParameterProvider::class)
    book: Book,
) {
    PreviewContainer {
        FavouriteBookItem(
            book = book,
            onClick = {},
            onFavouriteClick = {},
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
                FavouriteBookItem(
                    book = book,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }

            item {
                FavouriteBookItem(
                    book = book,
                    onClick = {},
                    onFavouriteClick = {},
                )
            }
        }
    }
}
