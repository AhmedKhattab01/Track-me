package com.example.trackme

import android.app.Application
import com.maltaisn.icondialog.pack.IconPack
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.mdi.createMaterialDesignIconPack
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class TrackApplication : Application() {
    lateinit var iconPack: IconPack
    override fun onCreate() {
        super.onCreate()

        loadIconPack()
    }

    private fun loadIconPack() {
        val loader = IconPackLoader(this)

        val iconPack = createMaterialDesignIconPack(loader)
        iconPack.loadDrawables(loader.drawableLoader)

        this.iconPack = iconPack
    }
}