package com.example.di

import com.example.data.local.dao.ListDao
import com.example.data.local.dao.TaskDao
import com.example.data.repo.AuthRepoImpl
import com.example.data.repo.ListRepoImpl
import com.example.data.repo.TaskRepoImpl
import com.example.domain.repo.AuthRepository
import com.example.domain.repo.ListRepository
import com.example.domain.repo.TaskRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTaskRepo(taskDao: TaskDao): TaskRepository {
        return TaskRepoImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideListRepo(listDao: ListDao): ListRepository {
        return ListRepoImpl(listDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepoImpl(firebaseAuth)
    }
}