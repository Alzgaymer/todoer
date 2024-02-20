package com.example.todoer.di

import com.example.todoer.domain.todo.TodosRepository
import com.example.todoer.platform.repositories.todo.TodosRepositoryFirestoreImpl
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object AppModule  {

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirestoreTodosRepository(firestore: FirebaseFirestore): TodosRepository {
        return TodosRepositoryFirestoreImpl(firestore)
    }

}