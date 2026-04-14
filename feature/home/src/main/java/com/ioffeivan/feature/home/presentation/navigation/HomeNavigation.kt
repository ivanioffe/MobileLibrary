package com.ioffeivan.feature.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.feature.home.presentation.composable.HomeRoute
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(HomeRoute, navOptions)

fun NavGraphBuilder.home(
    onNavigateToFavouriteBooks: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
) {
    composable<HomeRoute> {
        HomeRoute(
            onNavigateToFavouriteBooks = onNavigateToFavouriteBooks,
            onNavigateToSearch = onNavigateToSearch,
            onNavigateToBookDetails = onNavigateToBookDetails,
        )
    }
}
