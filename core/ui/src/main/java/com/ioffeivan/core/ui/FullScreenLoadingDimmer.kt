package com.ioffeivan.core.ui

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ioffeivan.core.designsystem.preview.PreviewContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DimmerLoadingOverlayScreen(
    modifier: Modifier = Modifier,
) {
    BasicAlertDialog(
        onDismissRequest = {},
        modifier = modifier,
    ) {
        LoadingScreen()
    }
}

@Preview
@Composable
private fun DimmerLoadingOverlayScreenPreview() {
    PreviewContainer {
        DimmerLoadingOverlayScreen()
    }
}
