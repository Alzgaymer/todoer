package com.example.todoer.domain.validation

import com.example.todoer.platform.repositories.todo.toLocalDateTime
import com.google.firebase.Timestamp

class ValidateRemindMeOn {

    fun validate(remindMeOn: List<Timestamp>, startDate: Timestamp): ValidationResult {
        if(remindMeOn.any{ it.toLocalDateTime().isAfter(startDate.toLocalDateTime()) })
            return ValidationResult(error = "reminder can`t be after start time")

        return ValidationResult(successful = true)
    }
}