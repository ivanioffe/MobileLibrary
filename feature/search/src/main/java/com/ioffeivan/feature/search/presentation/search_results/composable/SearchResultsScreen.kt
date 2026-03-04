package com.ioffeivan.feature.search.presentation.search_results.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.model.Books
import com.ioffeivan.core.ui.BookItem
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.core.ui.LoadingScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.feature.search.R
import com.ioffeivan.feature.search.presentation.search_results.SearchResultsEffect
import com.ioffeivan.feature.search.presentation.search_results.SearchResultsEvent
import com.ioffeivan.feature.search.presentation.search_results.SearchResultsState
import com.ioffeivan.feature.search.presentation.search_results.SearchResultsViewModel
import com.ioffeivan.feature.search.presentation.search_results.composable.component.Header

@Composable
internal fun SearchResultsRoute(
    onNavigateBack: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                SearchResultsEffect.NavigateToBack -> {
                    onNavigateBack()
                }

                is SearchResultsEffect.NavigateToBookDetails -> {
                    onNavigateToBookDetails(effect.id)
                }
            }
        },
    )

    SearchResultsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun SearchResultsScreen(
    state: SearchResultsState,
    onEvent: (SearchResultsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
    ) {
        Header(
            query = state.query,
            onBackClick = { onEvent(SearchResultsEvent.BackClick) },
            onSearchQueryBarClick = { onEvent(SearchResultsEvent.BackClick) },
        )

        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            when {
                state.isLoading ->
                    LoadingScreen()

                state.errorMessage != null ->
                    ErrorScreen(
                        message = state.errorMessage.asString(),
                        onRetry = { onEvent(SearchResultsEvent.RetryLoadClick) },
                    )

                state.books.items.isEmpty() -> EmptyState()

                else ->
                    SuccessState(
                        books = state.books.items,
                        onBookClick = { onEvent(SearchResultsEvent.BookClick(it)) },
                    )
            }
        }
    }
}

@Composable
private fun SuccessState(
    books: List<Book>,
    onBookClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
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
}

@Composable
private fun EmptyState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.search_no_results),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun SearchResultsScreenStatePreview() {
    PreviewContainer {
        SearchResultsScreen(
            state =
                SearchResultsState(
                    query = "Название",
                    books =
                        Books(
                            listOf(
                                Book(
                                    id = "1",
                                    title = "Название книги",
                                    authors = listOf("Автор И.О"),
                                    thumbnailUrl = "url",
                                ),
                                Book(
                                    id = "2",
                                    title = "Длинное название книги",
                                    authors = listOf("Автор И.О"),
                                    thumbnailUrl = "url",
                                ),
                                Book(
                                    id = "3",
                                    title = "Бумажная книга",
                                    authors = listOf("Автор Автор"),
                                    thumbnailUrl = "url",
                                ),
                            ),
                        ),
                    isLoading = false,
                    errorMessage = null,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SearchResultsScreenLoadingPreview() {
    PreviewContainer {
        SearchResultsScreen(
            state =
                SearchResultsState.initial("Название")
                    .copy(
                        isLoading = true,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SearchResultsScreenEmptyPreview() {
    PreviewContainer {
        SearchResultsScreen(
            state =
                SearchResultsState.initial("Очень очень очень очень интересная книга")
                    .copy(
                        books = Books(listOf()),
                        isLoading = false,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun SearchResultsScreenErrorPreview() {
    PreviewContainer {
        SearchResultsScreen(
            state =
                SearchResultsState.initial("Название")
                    .copy(
                        isLoading = false,
                        errorMessage = UiText.StaticString("Нет подключения к Интернету"),
                    ),
            onEvent = {},
        )
    }
}
