package com.example.todoer.domain.todo.serialize

import com.google.firebase.Timestamp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class SerializableTimestamp(val seconds: Long, val nanoseconds: Int)

object TimestampSerializer : KSerializer<Timestamp> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("Timestamp") {
        element<Long>("seconds")
        element<Int>("nanoseconds")
    }

    override fun serialize(encoder: Encoder, value: Timestamp) {
        val serializableTimestamp = SerializableTimestamp(value.seconds, value.nanoseconds)
        encoder.encodeSerializableValue(SerializableTimestamp.serializer(), serializableTimestamp)
    }

    override fun deserialize(decoder: Decoder): Timestamp {
        val serializableTimestamp = decoder.decodeSerializableValue(SerializableTimestamp.serializer())
        return Timestamp(serializableTimestamp.seconds, serializableTimestamp.nanoseconds)
    }
}