package com.example.todoer.domain.validation

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.google.firebase.Timestamp

class ValidateEndDate {

    fun validate(startDate: Timestamp, endDate: Timestamp): ValidationResult {
        if (startDate.equals(endDate))
            return ValidationResult(error = "End time shouldn`t be equal as start time")

        if (endDate.toLocalDateTime().isBefore(startDate.toLocalDateTime()))
            return ValidationResult(error = "end time can`t be before start time")

        return ValidationResult(successful = true)
    }
}