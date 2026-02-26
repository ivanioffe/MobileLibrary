package com.ioffeivan.feature.sign_in.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.sign_in.presentation.composable.SignInRoute
import kotlinx.serialization.Serializable

@Serializable
data object SignInRoute

fun NavGraphBuilder.signIn(
    onShowSnackbar: ShowSnackbar,
) {
    composable<SignInRoute> {
        SignInRoute(
            onShowSnackbar = onShowSnackbar,
        )
    }
}
