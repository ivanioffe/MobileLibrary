package com.ioffeivan.feature.search.presentation.search_input.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ioffeivan.feature.search.presentation.search_input.composable.SearchInputRoute
import kotlinx.serialization.Serializable

@Serializable
data object SearchInputRoute

fun NavGraphBuilder.searchInput(
    onNavigateBack: () -> Unit,
    onNavigateToSearchResults: (String) -> Unit,
) {
    composable<SearchInputRoute> {
        SearchInputRoute(
            onNavigateBack = onNavigateBack,
            onNavigateToSearchResults = onNavigateToSearchResults,
        )
    }
}
