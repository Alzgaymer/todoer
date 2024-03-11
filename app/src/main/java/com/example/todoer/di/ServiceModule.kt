package com.example.todoer.di

import com.example.todoer.domain.watch.todo.SendTodoService
import com.example.todoer.platform.service.watch.todo.SendTodoServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    @Singleton
    abstract fun bindSendTodoService(service: SendTodoServiceImpl) : SendTodoService
}