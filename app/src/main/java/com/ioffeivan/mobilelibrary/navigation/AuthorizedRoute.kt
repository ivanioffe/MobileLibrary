package com.ioffeivan.mobilelibrary.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.book_details.presentation.navigation.bookDetails
import com.ioffeivan.feature.favourite_books.presentation.navigation.favouriteBooks
import com.ioffeivan.feature.home.presentation.navigation.HomeRoute
import com.ioffeivan.feature.home.presentation.navigation.home
import com.ioffeivan.feature.search.presentation.navigation.search
import kotlinx.serialization.Serializable

@Serializable
data object AuthorizedRoute

fun NavGraphBuilder.authorized(
    onShowSnackbar: ShowSnackbar,
    onFavouriteClick: () -> Unit,
    onSearchClick: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToSearchResults: (String) -> Unit,
    onNavigateToBookDetails: (String) -> Unit,
) {
    navigation<AuthorizedRoute>(
        startDestination = HomeRoute,
    ) {
        home(
            onFavouriteClick = onFavouriteClick,
            onSearchClick = onSearchClick,
        )

        favouriteBooks(
            onShowSnackbar = onShowSnackbar,
            onNavigateBack = onNavigateBack,
            onNavigateToBookDetails = onNavigateToBookDetails,
        )

        search(
            onNavigateBack = onNavigateBack,
            onNavigateToSearchResults = onNavigateToSearchResults,
            onNavigateToBookDetails = onNavigateToBookDetails,
        )

        bookDetails(
            onShowSnackbar = onShowSnackbar,
            onNavigateBack = onNavigateBack,
        )
    }
}
