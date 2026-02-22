package com.ioffeivan.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme =
    lightColorScheme(
        primary = Grey900,
        onPrimary = Color.White,
        secondary = Grey200,
        onSecondary = Grey900,
        background = Color.White,
        onBackground = Grey900,
        surface = Color.White,
        onSurface = Grey900,
        surfaceVariant = Grey50,
        outline = Grey300,
        error = Red,
    )

@Composable
fun MobileLibraryTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content,
    )
}
