package com.ioffeivan.feature.home.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    onFavouriteClick: () -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.home),
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onFavouriteClick) {
                        PrimaryIcon(
                            id = PrimaryIcons.FilledFavourite,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        PrimaryIcon(
                            id = PrimaryIcons.Search,
                        )
                    }
                },
            )
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier =
                Modifier
                    .padding(innerPadding),
        )
    }
}
