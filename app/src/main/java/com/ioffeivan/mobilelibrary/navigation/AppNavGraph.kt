package com.ioffeivan.mobilelibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.book_details.presentation.navigation.navigateToBookDetails
import com.ioffeivan.feature.favourite_books.presentation.navigation.navigateToFavouriteBooks
import com.ioffeivan.feature.search.presentation.navigation.navigateToSearch
import com.ioffeivan.feature.search.presentation.search_results.navigation.navigateToSearchResults

@Composable
fun AppNavGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    onShowSnackbar: ShowSnackbar,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) AuthorizedRoute else UnauthorizedRoute,
        modifier = modifier,
    ) {
        unauthorized(
            onShowSnackbar = onShowSnackbar,
        )

        authorized(
            onShowSnackbar = onShowSnackbar,
            onFavouriteClick = navController::navigateToFavouriteBooks,
            onSearchClick = navController::navigateToSearch,
            onNavigateBack = navController::popBackStack,
            onNavigateToSearchResults = {
                navController.navigateToSearchResults(query = it)
            },
            onNavigateToBookDetails = navController::navigateToBookDetails,
        )
    }
}
