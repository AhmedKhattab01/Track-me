package com.example.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.TrackDatabase
import com.example.data.local.dao.TaskDao
import com.example.data.local.dao.SubTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideTaskDao(trackDatabase: TrackDatabase): SubTaskDao {
        return trackDatabase.getTaskDao()
    }

    @Provides
    @Singleton
    fun provideListDao(trackDatabase: TrackDatabase): TaskDao {
        return trackDatabase.getListDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TrackDatabase {
        return Room.databaseBuilder(
            context,
            TrackDatabase::class.java,
            "track_database"
        ).build()
    }
}