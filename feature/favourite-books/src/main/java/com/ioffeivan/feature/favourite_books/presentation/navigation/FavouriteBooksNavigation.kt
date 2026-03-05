package com.ioffeivan.feature.favourite_books.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.favourite_books.presentation.composable.FavouriteBooksRoute
import kotlinx.serialization.Serializable

@Serializable
data object FavouriteBooksRoute

fun NavController.navigateToFavouriteBooks(navOptions: NavOptions? = null) =
    navigate(FavouriteBooksRoute, navOptions)

fun NavGraphBuilder.favouriteBooks(
    onShowSnackbar: ShowSnackbar,
    onNavigateBack: () -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
) {
    composable<FavouriteBooksRoute> {
        FavouriteBooksRoute(
            onShowSnackbar = onShowSnackbar,
            onNavigateBack = onNavigateBack,
            onNavigateToBookDetails = onNavigateToBookDetails,
        )
    }
}
