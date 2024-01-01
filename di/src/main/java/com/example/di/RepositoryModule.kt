package com.example.di

import com.example.data.local.dao.TaskDao
import com.example.data.local.entities.TaskEntity
import com.example.data.repo.AuthRepoImpl
import com.example.data.repo.TasksRepoImpl
import com.example.domain.DtoToDomain
import com.example.domain.models.task.Task
import com.example.domain.repo.AuthRepository
import com.example.domain.repo.TasksRepository
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
    fun provideTasksRepo(
        taskDao: TaskDao,
        tasksEntitiesToTasks: DtoToDomain<List<TaskEntity>, List<Task>>,
        taskToTaskEntity: DtoToDomain<Task, TaskEntity>
    ): TasksRepository {
        return TasksRepoImpl(taskDao, tasksEntitiesToTasks, taskToTaskEntity)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepoImpl(firebaseAuth)
    }
}