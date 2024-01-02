package com.slayer.di

import com.slayer.data.local.entities.TaskEntity
import com.slayer.data.mappers.TaskEntitiesToTasks
import com.slayer.data.mappers.TaskToTaskEntity
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task
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