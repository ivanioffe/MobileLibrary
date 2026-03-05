package com.ioffeivan.feature.search.presentation.search_results.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ioffeivan.feature.search.presentation.search_results.SearchResultsViewModel
import com.ioffeivan.feature.search.presentation.search_results.composable.SearchResultsRoute
import kotlinx.serialization.Serializable

@Serializable
data class SearchResultsRoute(
    val query: String,
)

fun NavController.navigateToSearchResults(query: String, navOptions: NavOptions? = null) =
    navigate(SearchResultsRoute(query), navOptions)

fun NavGraphBuilder.searchResults(
    onNavigateBack: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
) {
    composable<SearchResultsRoute> { backStackEntry ->
        val (query) = backStackEntry.toRoute<SearchResultsRoute>()
        val creationCallback: (SearchResultsViewModel.Factory) -> SearchResultsViewModel =
            { factory ->
                factory.create(query)
            }

        SearchResultsRoute(
            onNavigateBack = onNavigateBack,
            onNavigateToBookDetails = onNavigateToBookDetails,
            viewModel =
                hiltViewModel(
                    key = query,
                    creationCallback = creationCallback,
                ),
        )
    }
}
