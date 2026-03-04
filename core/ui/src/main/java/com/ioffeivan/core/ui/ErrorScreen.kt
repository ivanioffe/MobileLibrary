package com.ioffeivan.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioffeivan.core.designsystem.component.PrimaryButton
import com.ioffeivan.core.designsystem.preview.PreviewContainer

@Composable
fun ErrorScreen(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        verticalArrangement =
            Arrangement.spacedBy(
                12.dp,
                Alignment.CenterVertically,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
        )

        PrimaryButton(
            text = stringResource(R.string.retry),
            onClick = onDebounceClick { onRetry() },
        )
    }
}

@Preview
@Composable
private fun ErrorScreenPreview() {
    PreviewContainer {
        ErrorScreen(
            message = "Ошибка загрузки",
            onRetry = {},
        )
    }
}
