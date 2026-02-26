package com.ioffeivan.mobilelibrary.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
data object AuthorizedRoute

@Serializable
data object HomeRoute

fun NavGraphBuilder.authorized() {
    navigation<AuthorizedRoute>(
        startDestination = HomeRoute,
    ) {
        composable<HomeRoute> {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .fillMaxSize(),
            ) {
                Text(text = "Home screen")
            }
        }
    }
}
