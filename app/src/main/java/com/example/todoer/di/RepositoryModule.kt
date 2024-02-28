package com.example.todoer.di

import com.example.todoer.domain.todo.TodoesRepository
import com.example.todoer.platform.repositories.todo.TodosRepositoryFirestoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {




    @Binds
    @Singleton
    abstract fun bindTodoesRepository(
        repo: TodosRepositoryFirestoreImpl
    ) : TodoesRepository
}