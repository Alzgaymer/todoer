package com.example.todoer.di

import com.example.todoer.domain.validation.ValidateEndDate
import com.example.todoer.domain.validation.ValidatePayload
import com.example.todoer.domain.validation.ValidateRemindMeOn
import com.example.todoer.domain.validation.ValidateStartDate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ValidatorsModule {

    @Provides
    fun provideValidateStartDate(): ValidateStartDate {
        return ValidateStartDate()
    }

    @Provides
    fun provideValidatePayload(): ValidatePayload {
        return ValidatePayload()
    }

    @Provides
    fun provideValidateEndDate(): ValidateEndDate {
        return ValidateEndDate()
    }

    @Provides
    fun provideValidateRemindMeOn(): ValidateRemindMeOn {
        return ValidateRemindMeOn()
    }
}