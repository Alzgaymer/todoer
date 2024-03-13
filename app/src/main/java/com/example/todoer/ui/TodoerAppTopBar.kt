package com.example.todoer.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoerAppTopBar(
    navigateUp: () -> Unit = {},
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {Text(text = stringResource(R.string.app_name), fontFamily = FontFamily.Monospace)},
        colors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
        actionIconContentColor = MaterialTheme.colorScheme.onBackground),
// TODO: make as profile
        actions = {
            IconButton(onClick = {  }) {
                Icon(
                    imageVector = Icons.Outlined.DateRange,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            // arrow back
            if (canNavigateBack)
                IconButton(onClick = navigateUp,) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)

                    )
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TodoerAppTopBarPreview() {
    TodoerAppTopBar({},true, TopAppBarDefaults.enterAlwaysScrollBehavior())
}