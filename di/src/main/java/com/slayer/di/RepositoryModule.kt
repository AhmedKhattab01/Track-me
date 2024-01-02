package com.slayer.di

import com.slayer.data.local.dao.TaskDao
import com.slayer.data.local.entities.TaskEntity
import com.slayer.data.repo.AuthRepoImpl
import com.slayer.data.repo.TasksRepoImpl
import com.slayer.domain.DtoToDomain
import com.slayer.domain.models.task.Task
import com.slayer.domain.repo.AuthRepository
import com.slayer.domain.repo.TasksRepository
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