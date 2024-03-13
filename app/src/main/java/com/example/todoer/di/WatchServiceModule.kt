package com.example.todoer.di

import android.content.Context
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object WatchServiceModule {

    @Provides
    @Singleton
    fun providesMessageClient(@ApplicationContext context: Context): MessageClient {
        return Wearable.getMessageClient(context)
    }

    @Provides
    @Singleton
    fun providesCapabilityClient(@ApplicationContext context: Context): CapabilityClient {
        return Wearable.getCapabilityClient(context)
    }

    @Provides
    @Singleton
    fun providesDataClient(@ApplicationContext context: Context): DataClient {
        return Wearable.getDataClient(context)
    }
}