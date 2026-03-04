package com.ioffeivan.feature.search.presentation.search_input.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.component.PrimarySearchBar
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.designsystem.theme.Grey200
import com.ioffeivan.core.designsystem.theme.Grey700
import com.ioffeivan.core.ui.ObserveEffectsWithLifecycle
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.feature.search.R
import com.ioffeivan.feature.search.presentation.search_input.SearchInputEffect
import com.ioffeivan.feature.search.presentation.search_input.SearchInputEvent
import com.ioffeivan.feature.search.presentation.search_input.SearchInputState
import com.ioffeivan.feature.search.presentation.search_input.SearchInputViewModel

@Composable
internal fun SearchInputRoute(
    onNavigateBack: () -> Unit,
    onNavigateToSearchResults: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchInputViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEffectsWithLifecycle(
        effects = viewModel.effect,
        onEffect = { effect ->
            when (effect) {
                SearchInputEffect.NavigateToBack -> {
                    onNavigateBack()
                }

                is SearchInputEffect.NavigateToSearchResults -> {
                    onNavigateToSearchResults(effect.query)
                }
            }
        },
    )

    SearchInputScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchInputScreen(
    state: SearchInputState,
    onEvent: (SearchInputEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.search_input_title),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick =
                            onDebounceClick {
                                onEvent(SearchInputEvent.BackClick)
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
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp),
        ) {
            PrimarySearchBar(
                query = state.query,
                onQueryChange = {
                    onEvent(SearchInputEvent.QueryChanged(it))
                },
                onSearch = { query ->
                    if (query.isNotEmpty()) {
                        onEvent(SearchInputEvent.SearchClick(query))
                    }
                },
            )

            if (state.recentSearches.isNotEmpty()) {
                RecentSearches(
                    recentSearches = state.recentSearches,
                    onQueryClick = {
                        onEvent(SearchInputEvent.RecentSearchClicked(it))
                    },
                    onDeleteQueryClick = {
                        onEvent(SearchInputEvent.RecentSearchDeleted(it))
                    },
                    onRecentSearchesClearClick = {
                        onEvent(SearchInputEvent.RecentSearchesCleared)
                    },
                    modifier =
                        Modifier
                            .padding(top = 16.dp),
                )
            }
        }
    }
}

@Composable
private fun RecentSearches(
    recentSearches: List<String>,
    onQueryClick: (String) -> Unit,
    onDeleteQueryClick: (String) -> Unit,
    onRecentSearchesClearClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.search_recent_label),
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = stringResource(R.string.search_clear_all),
                style =
                    MaterialTheme.typography.bodySmall.copy(
                        color = Grey700,
                    ),
                modifier =
                    Modifier
                        .clickable(
                            onClick =
                                onDebounceClick {
                                    onRecentSearchesClearClick()
                                },
                        ),
            )
        }

        FlowRow {
            recentSearches.forEach { query ->
                RecentSearchItem(
                    query = query,
                    onQueryClick = onQueryClick,
                    onDeleteQueryClick = onDeleteQueryClick,
                    modifier =
                        Modifier
                            .padding(
                                top = 2.dp,
                                end = 2.dp,
                                bottom = 2.dp,
                            ),
                )
            }
        }
    }
}

@Composable
private fun RecentSearchItem(
    query: String,
    onQueryClick: (String) -> Unit,
    onDeleteQueryClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Grey200)
                .clickable(
                    onClick =
                        onDebounceClick {
                            onQueryClick(query)
                        },
                ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier =
                Modifier
                    .padding(
                        start = 16.dp,
                        top = 8.dp,
                        end = 14.dp,
                        bottom = 8.dp,
                    ),
        ) {
            Text(
                text = query,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                    Modifier
                        .weight(
                            weight = 1f,
                            fill = false,
                        ),
            )

            IconButton(
                onClick =
                    onDebounceClick {
                        onDeleteQueryClick(query)
                    },
                modifier =
                    Modifier
                        .size(20.dp),
            ) {
                PrimaryIcon(
                    id = PrimaryIcons.Close,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SearchInputScreenPreview() {
    PreviewContainer {
        SearchInputScreen(
            state =
                SearchInputState(
                    query = "",
                    recentSearches =
                        listOf(
                            "Название",
                            "Название книги",
                            "Длинное название книги",
                            "Книга",
                            "Журнал",
                            "Интересная книга",
                            "Бумажная книга",
                            "Очень очень очень очень длинное название",
                        ),
                ),
            onEvent = {},
        )
    }
}
