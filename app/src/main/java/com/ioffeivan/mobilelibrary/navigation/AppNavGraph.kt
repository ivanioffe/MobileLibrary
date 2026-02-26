package com.ioffeivan.mobilelibrary.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.ioffeivan.core.ui.ShowSnackbar

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

        authorized()
    }
}
