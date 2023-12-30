package com.example.di

import com.example.data.local.dao.TaskDao
import com.example.data.local.dao.SubTaskDao
import com.example.data.repo.AuthRepoImpl
import com.example.data.repo.TasksRepoImpl
import com.example.data.repo.TaskRepoImpl
import com.example.domain.repo.AuthRepository
import com.example.domain.repo.TasksRepository
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
    fun provideTaskRepo(subTaskDao: SubTaskDao): TaskRepository {
        return TaskRepoImpl(subTaskDao)
    }

    @Provides
    @Singleton
    fun provideListRepo(taskDao: TaskDao): TasksRepository {
        return TasksRepoImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepoImpl(firebaseAuth)
    }
}