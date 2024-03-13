package com.example.todoer.domain.validation

import android.util.Patterns

class ValidateEmail {
    fun validate(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(error = "The email can't be blank")
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(error = "That's not a valid email")
        }
        return ValidationResult(successful = true)
    }
}