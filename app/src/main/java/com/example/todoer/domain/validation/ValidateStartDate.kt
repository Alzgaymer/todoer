package com.example.todoer.domain.validation

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.google.firebase.Timestamp

class ValidateStartDate {

    fun validate(startDate: Timestamp, endDate: Timestamp): ValidationResult {
        if (startDate.equals(endDate))
            return ValidationResult(error = "Start time shouldn`t be equal as end time")

        if (startDate.toLocalDateTime().isAfter(endDate.toLocalDateTime()))
            return ValidationResult(error = "start time can`t be before end time")

        return ValidationResult(successful = true)
    }
}