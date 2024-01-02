package com.slayer.di

import com.slayer.domain.repo.AuthRepository
import com.slayer.domain.repo.TasksRepository
import com.slayer.domain.usecases.auth_usecases.LoginUseCase
import com.slayer.domain.usecases.tasks_usecases.DeleteTaskUseCase
import com.slayer.domain.usecases.tasks_usecases.GetTasksUseCase
import com.slayer.domain.usecases.tasks_usecases.InsertTaskUseCase
import com.slayer.domain.usecases.tasks_usecases.UpdateTaskUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllTasksUseCase(tasksRepository: TasksRepository) : GetTasksUseCase {
        return GetTasksUseCase(tasksRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(tasksRepository: TasksRepository) : DeleteTaskUseCase {
        return DeleteTaskUseCase(tasksRepository)
    }

    @Provides
    @Singleton
    fun provideInsertTaskUseCase(tasksRepository: TasksRepository) : InsertTaskUseCase {
        return InsertTaskUseCase(tasksRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(tasksRepository: TasksRepository) : UpdateTaskUseCase {
        return UpdateTaskUseCase(tasksRepository)
    }
}