package com.ioffeivan.feature.search.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.ioffeivan.feature.search.presentation.search_input.navigation.SearchInputRoute
import com.ioffeivan.feature.search.presentation.search_input.navigation.searchInput
import com.ioffeivan.feature.search.presentation.search_results.navigation.searchResults
import kotlinx.serialization.Serializable

@Serializable
data object SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(SearchRoute, navOptions)

fun NavGraphBuilder.search(
    onNavigateBack: () -> Unit,
    onNavigateToSearchResults: (String) -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
) {
    navigation<SearchRoute>(
        startDestination = SearchInputRoute,
    ) {
        searchInput(
            onNavigateBack = onNavigateBack,
            onNavigateToSearchResults = onNavigateToSearchResults,
        )

        searchResults(
            onNavigateBack = onNavigateBack,
            onNavigateToBookDetails = onNavigateToBookDetails,
        )
    }
}
