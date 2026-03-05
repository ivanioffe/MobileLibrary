package com.ioffeivan.feature.book_details.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.designsystem.theme.Red
import com.ioffeivan.core.ui.DimmerLoadingOverlayScreen
import com.ioffeivan.core.ui.ErrorScreen
import com.ioffeivan.core.ui.LoadingScreen
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.core.ui.UiText
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.feature.book_details.R
import com.ioffeivan.feature.book_details.domain.model.BookDetails
import com.ioffeivan.feature.book_details.presentation.BookDetailsEffect
import com.ioffeivan.feature.book_details.presentation.BookDetailsEvent
import com.ioffeivan.feature.book_details.presentation.BookDetailsState
import com.ioffeivan.feature.book_details.presentation.BookDetailsViewModel
import com.ioffeivan.feature.book_details.presentation.preview.BookDetailsPreviewParameterProvider
import com.ioffeivan.core.ui.R as CoreUiR

@Composable
internal fun BookDetailsRoute(
    onShowSnackbar: ShowSnackbar,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookDetailsViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                BookDetailsEffect.NavigateToBack -> {
                    onNavigateBack()
                }

                is BookDetailsEffect.ShowError -> {
                    onShowSnackbar(effect.errorMessage.asString(context), null)
                }
            }
        },
    )

    BookDetailsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BookDetailsScreen(
    state: BookDetailsState,
    onEvent: (BookDetailsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.about_book),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick =
                            onDebounceClick {
                                onEvent(BookDetailsEvent.BackClicked)
                            },
                    ) {
                        PrimaryIcon(id = PrimaryIcons.ArrowBack)
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            when {
                state.isLoading -> LoadingScreen()

                state.errorMessage != null ->
                    ErrorScreen(
                        message = state.errorMessage.asString(),
                        onRetry = { onEvent(BookDetailsEvent.RetryLoadClicked) },
                    )

                else ->
                    SuccessState(
                        state = state,
                        onFavouriteClick = { bookId, isFavourite ->
                            onEvent(
                                BookDetailsEvent.FavouriteClicked(bookId, isFavourite),
                            )
                        },
                    )
            }
        }
    }
}

@Composable
private fun SuccessState(
    state: BookDetailsState,
    onFavouriteClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    BookDetailsUi(
        bookDetails = state.bookDetails,
        onFavouriteClick = onFavouriteClick,
        modifier = modifier,
    )

    if (state.isToggleFavouriteLoading) {
        DimmerLoadingOverlayScreen()
    }
}

@Composable
private fun BookDetailsUi(
    bookDetails: BookDetails,
    onFavouriteClick: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = bookDetails.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier =
                Modifier
                    .height(350.dp)
                    .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = bookDetails.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style =
                        MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        ),
                    modifier =
                        Modifier
                            .weight(1f),
                )

                IconButton(
                    onClick =
                        onDebounceClick {
                            onFavouriteClick(
                                bookDetails.id,
                                bookDetails.isFavourite,
                            )
                        },
                ) {
                    PrimaryIcon(
                        id = if (bookDetails.isFavourite) PrimaryIcons.FilledFavourite else PrimaryIcons.OutlinedFavourite,
                        tint = Red,
                        modifier =
                            Modifier
                                .size(28.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = bookDetails.authors ?: stringResource(CoreUiR.string.author_not_specified),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                    Modifier
                        .fillMaxWidth(),
            )

            if (bookDetails.description != null) {
                Text(
                    text = bookDetails.description,
                    style =
                        MaterialTheme.typography.bodyMedium.copy(
                            lineHeight = 22.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 24.dp,
                                bottom = 8.dp,
                            ),
                )
            }
        }
    }
}

@Preview
@Composable
private fun BookDetailsScreenSuccessPreview(
    @PreviewParameter(BookDetailsPreviewParameterProvider::class)
    bookDetails: BookDetails,
) {
    PreviewContainer {
        BookDetailsScreen(
            state =
                BookDetailsState.initial().copy(
                    bookDetails = bookDetails,
                    isLoading = false,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun BookDetailsScreenFavouriteLoadingPreview(
    @PreviewParameter(BookDetailsPreviewParameterProvider::class)
    bookDetails: BookDetails,
) {
    PreviewContainer {
        BookDetailsScreen(
            state =
                BookDetailsState.initial().copy(
                    bookDetails = bookDetails,
                    isLoading = false,
                    isToggleFavouriteLoading = true,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun BookDetailsScreenLoadingPreview() {
    PreviewContainer {
        BookDetailsScreen(
            state =
                BookDetailsState.initial().copy(
                    isLoading = true,
                ),
            onEvent = {},
        )
    }
}

@Preview
@Composable
private fun BookDetailsScreenErrorPreview() {
    PreviewContainer {
        BookDetailsScreen(
            state =
                BookDetailsState.initial().copy(
                    isLoading = false,
                    errorMessage = UiText.StaticString("Не удалось загрузить данные о книге"),
                ),
            onEvent = {},
        )
    }
}
