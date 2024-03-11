package com.example.todoer.wear.di

import com.example.todoer.wear.service.todo.TodoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideTodoService(): TodoService {
        return TodoService()
    }
}

@Module
@InstallIn(ServiceComponent::class)
object ForService {

    @Provides
    fun provideScope(): CoroutineContext = Job() + Dispatchers.Main.immediate
}