package com.example.data.di

import com.example.data.local.dao.ListDao
import com.example.data.local.dao.TaskDao
import com.example.data.repo.ListRepoImpl
import com.example.data.repo.TaskRepoImpl
import com.example.domain.repo.ListRepository
import com.example.domain.repo.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {
    @Provides
    fun provideTaskRepo(taskDao: TaskDao) : TaskRepository {
        return TaskRepoImpl(taskDao)
    }

    @Provides
    fun provideListRepo(listDao: ListDao) : ListRepository {
        return ListRepoImpl(listDao)
    }
}