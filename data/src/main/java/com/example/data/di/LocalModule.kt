package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.TrackDatabase
import com.example.data.local.dao.ListDao
import com.example.data.local.dao.TaskDao
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
    fun provideTaskDao(trackDatabase: TrackDatabase): TaskDao {
        return trackDatabase.getTaskDao()
    }

    @Provides
    @Singleton
    fun provideListDao(trackDatabase: TrackDatabase): ListDao {
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