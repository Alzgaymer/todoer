package com.example.todoer.domain.validation

class ValidatePayload {

    fun validate(payload: String): ValidationResult {
        if (payload.isBlank())
            return ValidationResult(error = "This field shouldn`t be empty")

        return ValidationResult(successful = true)
    }
}