package com.example.todoer.di

import android.content.Context
import com.example.todoer.domain.auth.AuthClient
import com.example.todoer.platform.auth.google.GoogleAuthClient
import com.google.android.gms.auth.api.identity.Identity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideGoogleAuthClient(@ApplicationContext context: Context): AuthClient {
        return GoogleAuthClient(
            context,
            Identity.getSignInClient(context)
            )
    }

    @Provides
    @Named("userID")
    fun provideUserID(authClient: AuthClient): String? = authClient.getSignedInUser()?.userID
}