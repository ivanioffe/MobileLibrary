package com.ioffeivan.feature.home.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.model.Book
import com.ioffeivan.core.model.Books
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.core.ui.LoadingScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.core.ui.books
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.core.ui.preview.BooksPreviewParameterProvider
import com.ioffeivan.feature.home.R
import com.ioffeivan.feature.home.presentation.HomeEffect
import com.ioffeivan.feature.home.presentation.HomeEvent
import com.ioffeivan.feature.home.presentation.HomeState
import com.ioffeivan.feature.home.presentation.HomeViewModel

@Composable
internal fun HomeRoute(
    modifier: Modifier = Modifier,
    onNavigateToFavouriteBooks: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                HomeEffect.NavigateToFavouriteBooks -> onNavigateToFavouriteBooks()

                HomeEffect.NavigateToSearch -> onNavigateToSearch()

                is HomeEffect.NavigateToBookDetails -> onNavigateToBookDetails(effect.id)
            }
        },
    )

    HomeScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onDebounceClick { onEvent(HomeEvent.FavouriteBooksClicked) },
                    ) {
                        PrimaryIcon(
                            id = PrimaryIcons.FilledFavourite,
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onDebounceClick { onEvent(HomeEvent.SearchClicked) },
                    ) {
                        PrimaryIcon(
                            id = PrimaryIcons.Search,
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
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
                            onRetry = { onEvent(HomeEvent.RetryLoadClicked) },
                        )

                    state.recommendedBooks.items.isEmpty() -> EmptyState()

                    else ->
                        SuccessState(
                            books = state.recommendedBooks.items,
                            onBookClick = { onEvent(HomeEvent.RecommendedBookClicked(it)) },
                            modifier =
                                Modifier
                                    .fillMaxSize(),
                        )
                }
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
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = stringResource(R.string.for_you),
                style = MaterialTheme.typography.titleLarge,
                modifier =
                    Modifier
                        .padding(
                            horizontal = 18.dp,
                            vertical = 8.dp,
                        ),
            )
        }

        books(
            books = books,
            onBookClick = onBookClick,
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(
            text = stringResource(R.string.no_recommended_books),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Preview
@Composable
private fun HomeScreenSuccessPreview(
    @PreviewParameter(BooksPreviewParameterProvider::class)
    books: Books,
) {
    PreviewContainer {
        HomeScreen(
            state =
                HomeState(
                    recommendedBooks = books,
                    isLoading = false,
                    errorMessage = null,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    PreviewContainer {
        HomeScreen(
            state =
                HomeState.initial()
                    .copy(
                        isLoading = true,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenEmptyPreview() {
    PreviewContainer {
        HomeScreen(
            state =
                HomeState.initial()
                    .copy(
                        recommendedBooks = Books(listOf()),
                        isLoading = false,
                    ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun HomeScreenErrorPreview() {
    PreviewContainer {
        HomeScreen(
            state =
                HomeState.initial()
                    .copy(
                        isLoading = false,
                        errorMessage = UiText.StaticString("Нет подключения к Интернету"),
                    ),
            onEvent = {},
        )
    }
}
