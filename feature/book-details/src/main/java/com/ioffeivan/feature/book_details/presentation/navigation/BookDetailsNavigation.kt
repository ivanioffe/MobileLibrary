package com.ioffeivan.feature.book_details.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.book_details.presentation.BookDetailsViewModel
import com.ioffeivan.feature.book_details.presentation.composable.BookDetailsRoute
import kotlinx.serialization.Serializable

@Serializable
data class BookDetailsRoute(
    val bookId: String,
)

fun NavController.navigateToBookDetails(bookId: String, navOptions: NavOptions? = null) =
    navigate(BookDetailsRoute(bookId), navOptions)

fun NavGraphBuilder.bookDetails(
    onShowSnackbar: ShowSnackbar,
    onNavigateBack: () -> Unit,
) {
    composable<BookDetailsRoute> { backStackEntry ->
        val (bookId) = backStackEntry.toRoute<BookDetailsRoute>()
        val creationCallback: (BookDetailsViewModel.Factory) -> BookDetailsViewModel =
            { factory ->
                factory.create(bookId)
            }

        BookDetailsRoute(
            onShowSnackbar = onShowSnackbar,
            onNavigateBack = onNavigateBack,
            viewModel =
                hiltViewModel(
                    key = bookId,
                    creationCallback = creationCallback,
                ),
        )
    }
}
