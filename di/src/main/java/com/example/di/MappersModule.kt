package com.example.di

import com.example.data.local.entities.TaskEntity
import com.example.data.mappers.TaskEntitiesToTasks
import com.example.data.mappers.TaskToTaskEntity
import com.example.domain.DtoToDomain
import com.example.domain.models.task.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MappersModule {
    @Provides
    @Singleton
    fun provideTasksEntitiesToTasksMapper() : DtoToDomain<List<TaskEntity>, List<Task>> = TaskEntitiesToTasks()
    @Provides
    @Singleton
    fun provideTaskToTaskEntityMapper() : DtoToDomain<Task, TaskEntity> = TaskToTaskEntity()
}