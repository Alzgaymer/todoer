package com.example.todoer.wear.di

import android.content.Context
import com.example.todoer.wear.domain.auth.AuthClient
import com.example.todoer.wear.platform.auth.google.GoogleAuthClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): AuthClient {
        return GoogleAuthClient(context)
    }
}