package com.example.todoer.domain.todo.serialize

import com.example.todoer.wear.domain.todo.Todo
import com.example.todoer.wear.domain.todo.dto.TodoJson
import com.example.todoer.wear.domain.todo.dto.toJsonDTO
import com.example.todoer.wear.domain.todo.dto.toTodo
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializer(forClass = List::class)
object TodoListSerializer : KSerializer<List<Todo>> {
    override val descriptor: SerialDescriptor = ListSerializer(TodoJson.serializer()).descriptor

    override fun serialize(encoder: Encoder, value: List<Todo>) {
        val todoJsonList = value.map { it.toJsonDTO() }
        encoder.encodeSerializableValue(ListSerializer(TodoJson.serializer()), todoJsonList)
    }

    override fun deserialize(decoder: Decoder): List<Todo> {
        val todoJsonList = decoder.decodeSerializableValue(ListSerializer(TodoJson.serializer()))
        return todoJsonList.map { it.toTodo() }
    }
}