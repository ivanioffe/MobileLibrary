package com.ioffeivan.mobilelibrary.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.ioffeivan.core.ui.ShowSnackbar
import com.ioffeivan.feature.sign_in.presentation.navigation.SignInRoute
import com.ioffeivan.feature.sign_in.presentation.navigation.signIn
import kotlinx.serialization.Serializable

@Serializable
data object UnauthorizedRoute

fun NavGraphBuilder.unauthorized(
    onShowSnackbar: ShowSnackbar,
) {
    navigation<UnauthorizedRoute>(
        startDestination = SignInRoute,
    ) {
        signIn(
            onShowSnackbar = onShowSnackbar,
        )
    }
}
