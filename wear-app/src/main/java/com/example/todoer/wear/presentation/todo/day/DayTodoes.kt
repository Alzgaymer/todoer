package com.example.todoer.wear.presentation.todo.day

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.wear.compose.material.Text
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.compose.layout.ScalingLazyColumnDefaults
import com.google.android.horologist.compose.layout.ScreenScaffold
import com.google.android.horologist.compose.rotaryinput.rotaryWithScroll

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun DayTodoes(text: String) {
    val scrollState = ScrollState(0)

    /* If you have enough items in your list, use [ScalingLazyColumn] which is an optimized
     * version of LazyColumn for wear devices with some added features. For more information,
     * see d.android.com/wear/compose.
     */
    ScreenScaffold(scrollState = scrollState) {
        val padding = ScalingLazyColumnDefaults.padding(
            first = ScalingLazyColumnDefaults.ItemType.Text,
            last = ScalingLazyColumnDefaults.ItemType.Chip
        )()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .rotaryWithScroll(scrollState)
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text)
        }
    }
}


@Preview(device = Devices.WEAR_OS_SMALL_ROUND)
@Composable
fun PreviewDayTodoes() {
    DayTodoes(text = "Hello")
}