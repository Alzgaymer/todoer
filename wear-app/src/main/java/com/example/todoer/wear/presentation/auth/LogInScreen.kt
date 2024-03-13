@file:OptIn(ExperimentalWearFoundationApi::class)

package com.example.todoer.wear.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.example.todoer.wear.presentation.theme.TodoerTheme
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun LogInScreen(
    state: LogInState,
    scrollState: ScrollState = rememberScrollState(),
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
        .fillMaxSize()
        .rotaryWithScroll(scrollState)
        .padding(
            top = 26.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 26.dp)
    ){
        Button(onClick = onClick) {
            Text(text = "Sign in", color = Color.White)
        }
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
fun PreviewLogInScreen() {
    TodoerTheme {
       Scaffold {
           LogInScreen(state = LogInState()) {}
       }
    }
}