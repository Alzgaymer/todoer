package com.example.todoer.domain.validation

data class ValidationResult(
    val successful: Boolean = false,
    val error: String? = null
)
