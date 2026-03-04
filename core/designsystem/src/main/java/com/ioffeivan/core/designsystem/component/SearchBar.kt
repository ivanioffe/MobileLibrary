package com.ioffeivan.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.preview.PreviewContainer

@Composable
fun PrimarySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(
                text = "Поиск",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        leadingIcon = {
            PrimaryIcon(
                id = PrimaryIcons.Search,
            )
        },
        trailingIcon = {
            IconButton(
                onClick =
                    { onQueryChange("") },
            ) {
                PrimaryIcon(
                    id = PrimaryIcons.Close,
                )
            }
        },
        keyboardOptions =
            KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
        keyboardActions =
            KeyboardActions(
                onSearch = { onSearch(query) },
            ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        modifier =
            modifier
                .fillMaxWidth(),
    )
}

@Preview
@Composable
private fun SearchFieldPreview() {
    PreviewContainer {
        PrimarySearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            modifier =
                Modifier
                    .padding(16.dp),
        )
    }
}
