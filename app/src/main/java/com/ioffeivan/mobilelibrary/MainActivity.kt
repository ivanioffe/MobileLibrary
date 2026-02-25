package com.ioffeivan.mobilelibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ioffeivan.core.designsystem.theme.MobileLibraryTheme
import com.ioffeivan.mobilelibrary.ui.AppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MobileLibraryTheme {
                Surface(
                    modifier =
                        Modifier
                            .windowInsetsPadding(WindowInsets.safeDrawing),
                ) {
                    val uiState by mainActivityViewModel.state.collectAsStateWithLifecycle()

                    if (uiState !is MainActivityUiState.Loading) {
                        AppScreen(
                            isLoggedIn = uiState is MainActivityUiState.LoggedIn,
                        )
                    }
                }
            }
        }
    }
}
