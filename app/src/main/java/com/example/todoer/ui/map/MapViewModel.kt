package com.example.todoer.ui.map

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
): ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state


    fun setLocation(latitude: Double, longitude: Double) {
        _state.value = _state.value.copy(location =
        MarkerState(
            position = LatLng(latitude,longitude)))
    }

    fun deleteLocation() {
        _state.value = _state.value.copy(location = null)
    }
}