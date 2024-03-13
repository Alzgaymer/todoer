package com.example.todoer.ui.map

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MarkerState


data class MapState(
    val properties: MapProperties = MapProperties(),
    var location: MarkerState? = null,
)

fun LatLng.toGeoPoint(): GeoPoint = GeoPoint(latitude, longitude)