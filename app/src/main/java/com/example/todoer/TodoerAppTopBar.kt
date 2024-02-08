package com.example.todoer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoerAppTopBar(
    navigateUp: () -> Unit = {},
    canNavigateBack: Boolean,
    scrollBehavior: TopAppBarScrollBehavior
) {
    CenterAlignedTopAppBar(
        title = {Text(text = stringResource(R.string.app_name), fontFamily = FontFamily.Monospace)},
        colors = TopAppBarDefaults.smallTopAppBarColors(),
        actions = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            // arrow back
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
                // menu icon
            } else {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = stringResource(R.string.menu_button)
                    )
                }
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