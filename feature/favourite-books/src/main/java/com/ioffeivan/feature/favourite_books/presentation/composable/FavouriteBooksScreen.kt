package com.ioffeivan.feature.favourite_books.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.model.Books
import com.ioffeivan.core.ui.DimmerLoadingOverlayScreen
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.core.ui.LoadingScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.core.ui.preview.BooksPreviewParameterProvider
import com.ioffeivan.feature.favourite_books.presentation.FavouriteBooksEffect
import com.ioffeivan.feature.favourite_books.presentation.FavouriteBooksEvent
import com.ioffeivan.feature.favourite_books.presentation.FavouriteBooksState
import com.ioffeivan.feature.favourite_books.presentation.FavouriteBooksViewModel
import com.ioffeivan.feature.favourite_books.presentation.composable.component.FavouriteBookItem

@Composable
internal fun FavouriteBooksRoute(
    onShowSnackbar: ShowSnackbar,
    onNavigateBack: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavouriteBooksViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                FavouriteBooksEffect.NavigateToBack -> {
                    onNavigateBack()
                }

                is FavouriteBooksEffect.NavigateToBookDetails -> {
                    onNavigateToBookDetails(effect.id)
                }

                is FavouriteBooksEffect.ShowError -> {
                    onShowSnackbar(effect.errorMessage.asString(context), null)
                }
            }
        },
    )

    FavouriteBooksScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FavouriteBooksScreen(
    state: FavouriteBooksState,
    onEvent: (FavouriteBooksEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Избранное",
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick =
                            onDebounceClick {
                                onEvent(FavouriteBooksEvent.BackClicked)
                            },
                    ) {
                        PrimaryIcon(
                            id = PrimaryIcons.ArrowBack,
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when {
                state.isLoading ->
                    LoadingScreen()

                state.errorMessage != null ->
                    ErrorScreen(
                        message = state.errorMessage.asString(),
                        onRetry = {
                            onEvent(FavouriteBooksEvent.RetryLoadClicked)
                        },
                    )

                state.favouriteBooks.items.isEmpty() -> EmptyState()

                else ->
                    SuccessState(
                        state = state,
                        onBookClick = {
                            onEvent(FavouriteBooksEvent.BookClicked(it))
                        },
                        onFavouriteClick = {
                            onEvent(FavouriteBooksEvent.FavouriteClicked(it))
                        },
                        onRemoveConfirm = {
                            onEvent(FavouriteBooksEvent.RemoveBookConfirmed(it))
                        },
                        onRemoveDismiss = {
                            onEvent(FavouriteBooksEvent.RemoveBookDismissed)
                        },
                        modifier =
                            Modifier
                                .fillMaxSize(),
                    )
            }
        }
    }
}

@Composable
private fun SuccessState(
    state: FavouriteBooksState,
    onBookClick: (String) -> Unit,
    onFavouriteClick: (String) -> Unit,
    onRemoveConfirm: (String) -> Unit,
    onRemoveDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
    ) {
        items(
            items = state.favouriteBooks.items,
            key = { it.id },
        ) { book ->
            FavouriteBookItem(
                book = book,
                onClick = onBookClick,
                onFavouriteClick = onFavouriteClick,
                modifier =
                    Modifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp,
                        ),
            )
        }
    }

    if (state.bookForRemoveId != null && !state.isBookRemoving) {
        AlertDialog(
            onDismissRequest = {
                onRemoveDismiss()
            },
            text = {
                Text(
                    text = "Вы уверены что хотите удалить книгу из Избранного? Это действие нельзя будет отменить",
                )
            },
            confirmButton = {
                Button(
                    onClick =
                        onDebounceClick {
                            onRemoveConfirm(state.bookForRemoveId)
                        },
                ) {
                    Text(text = "Да")
                }
            },
            dismissButton = {
                Button(
                    onClick =
                        onDebounceClick {
                            onRemoveDismiss()
                        },
                ) {
                    Text(text = "Нет")
                }
            },
        )
    }

    if (state.isBookRemoving) {
        DimmerLoadingOverlayScreen()
    }
}

@Composable
private fun EmptyState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        Text(
            text = "Вы пока не добавили ни одной книги",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenSuccessPreview(
    @PreviewParameter(BooksPreviewParameterProvider::class)
    books: Books,
) {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial().copy(
                    favouriteBooks = books,
                    isLoading = false,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenSuccessWithDialogPreview(
    @PreviewParameter(BooksPreviewParameterProvider::class)
    books: Books,
) {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial().copy(
                    favouriteBooks = books,
                    isLoading = false,
                    bookForRemoveId = "id",
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenSuccessIsBookRemovingPreview(
    @PreviewParameter(BooksPreviewParameterProvider::class)
    books: Books,
) {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial().copy(
                    favouriteBooks = books,
                    isLoading = false,
                    bookForRemoveId = "",
                    isBookRemoving = true,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenLoadingPreview() {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial()
                    .copy(
                        isLoading = true,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenEmptyPreview() {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial()
                    .copy(
                        favouriteBooks = Books(listOf()),
                        isLoading = false,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun FavouriteBooksScreenErrorPreview() {
    PreviewContainer {
        FavouriteBooksScreen(
            state =
                FavouriteBooksState.initial()
                    .copy(
                        isLoading = false,
                        errorMessage = UiText.StaticString("Нет подключения к Интернету"),
                    ),
            onEvent = {},
        )
    }
}
