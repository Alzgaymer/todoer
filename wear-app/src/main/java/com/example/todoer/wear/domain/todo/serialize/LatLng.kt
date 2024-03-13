package com.example.todoer.domain.todo.serialize

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class SerializableLatLng(val latitude: Double, val longitude: Double)

object LatLngSerializer : KSerializer<LatLng> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("LatLng") {
            element<Double>("latitude")
            element<Double>("longitude")
        }

    override fun serialize(encoder: Encoder, value: LatLng) {
        val serializableLatLng = SerializableLatLng(value.latitude, value.longitude)
        encoder.encodeSerializableValue(SerializableLatLng.serializer(), serializableLatLng)
    }

    override fun deserialize(decoder: Decoder): LatLng {
        val serializableLatLng = decoder.decodeSerializableValue(SerializableLatLng.serializer())
        return LatLng(serializableLatLng.latitude, serializableLatLng.longitude)
    }
}