package com.example.trackme.di

import android.content.Context
import com.maltaisn.icondialog.pack.IconDrawableLoader
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack
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
    fun provideIconLoader(@ApplicationContext context: Context) : IconPackLoader {
        return IconPackLoader(context)
    }

    @Provides
    @Singleton
    fun provideIconPack(loader: IconPackLoader) : IconPack {
        return createDefaultIconPack(loader)
    }

    @Provides
    @Singleton
    fun provideDrawableLoader(loader: IconPackLoader) : IconDrawableLoader {
        return loader.drawableLoader
    }
}