package com.ioffeivan.feature.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.feature.home.presentation.composable.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(HomeRoute, navOptions)

fun NavGraphBuilder.home(
    onFavouriteClick: () -> Unit,
    onSearchClick: () -> Unit,
) {
    composable<HomeRoute> {
        HomeScreen(
            onFavouriteClick = onFavouriteClick,
            onSearchClick = onSearchClick,
        )
    }
}
