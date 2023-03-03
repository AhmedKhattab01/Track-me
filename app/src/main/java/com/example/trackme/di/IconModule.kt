package com.example.trackme.di

import android.content.Context
import com.example.trackme.ui.core.utils.IconUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object IconModule {

    @Provides
    @Singleton
    fun provideIconUtil(@ApplicationContext context: Context) : IconUtil {
        return IconUtil(context)
    }
}