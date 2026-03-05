package com.ioffeivan.feature.search.presentation.search_results.composable.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcon
import com.ioffeivan.core.designsystem.component.icon.PrimaryIcons
import com.ioffeivan.core.designsystem.theme.Grey200
import com.ioffeivan.core.ui.onDebounceClick

@Composable
internal fun Header(
    query: String,
    onSearchQueryBarClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier =
                modifier
                    .padding(
                        start = 4.dp,
                        top = 8.dp,
                        end = 16.dp,
                    ),
        ) {
            IconButton(
                onClick =
                    onDebounceClick {
                        onBackClick()
                    },
            ) {
                PrimaryIcon(
                    id = PrimaryIcons.ArrowBack,
                )
            }

            SearchQueryBar(
                query = query,
                onClick = onSearchQueryBarClick,
            )
        }

        HorizontalDivider()
    }
}

@Composable
private fun SearchQueryBar(
    query: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Grey200)
                .clickable(
                    onClick = onDebounceClick { onClick() },
                ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                modifier
                    .fillMaxWidth(),
        ) {
            Text(
                text = query,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium,
                modifier =
                    Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 20.dp,
                        ),
            )
        }
    }
}
