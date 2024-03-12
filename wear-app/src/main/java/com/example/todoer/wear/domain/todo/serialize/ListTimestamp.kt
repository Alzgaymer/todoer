package com.example.todoer.domain.todo.serialize

import com.google.firebase.Timestamp
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = List::class)
object TimestampListSerializer : KSerializer<List<Timestamp>> {
    override val descriptor: SerialDescriptor = ListSerializer(TimestampSerializer).descriptor

    override fun serialize(encoder: Encoder, value: List<Timestamp>) {
        encoder.encodeSerializableValue(ListSerializer(TimestampSerializer), value)
    }

    override fun deserialize(decoder: Decoder): List<Timestamp> {
        return decoder.decodeSerializableValue(ListSerializer(TimestampSerializer))
    }
}