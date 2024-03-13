package com.example.todoer.ui.map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.todoer.ui.TodoerAppTopBar
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    uiState: MapState,
    navigateUp: () -> Unit,
    onLongClick: (Marker) -> Unit,
    onPositionChange: (LatLng) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        topBar = { TodoerAppTopBar(
            navigateUp = navigateUp,
            canNavigateBack = true,
            scrollBehavior = scrollBehavior
        )}
    ) {
        Map(
            properties =  uiState.properties,
            location = uiState.location,
            onClick =  onPositionChange,
            onLongClick = onLongClick,
            contentPadding = it
        )
    }
}

@Composable
fun Map(
    properties: MapProperties,
    location: MarkerState?,
    onClick: (LatLng) -> Unit,
    onLongClick: (Marker) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    val uiSettings = remember { MapUiSettings(zoomControlsEnabled = false) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        properties = properties,
        uiSettings = uiSettings,
        onMapClick = { onClick(it) },
        contentPadding = contentPadding
    ) {

        if (location != null) {
            Marker(
                state = location,
                title = "Todo spot ${location.position.latitude}, ${location.position.longitude}",
                snippet = "Long click to delete",
                onInfoWindowLongClick = { onLongClick(it) },
                onClick = {
                    it.showInfoWindow()
                    true
                }
            )
        }
    }
}


