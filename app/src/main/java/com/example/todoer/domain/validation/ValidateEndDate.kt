package com.example.todoer.domain.validation

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.google.firebase.Timestamp

class ValidateEndDate {

    fun validate(startDate: Timestamp, endDate: Timestamp): ValidationResult {
        if (endDate.toLocalDateTime().isBefore(endDate.toLocalDateTime()))
            return ValidationResult(error = "end time can`t be before start time")

        return ValidationResult(successful = true)
    }
}