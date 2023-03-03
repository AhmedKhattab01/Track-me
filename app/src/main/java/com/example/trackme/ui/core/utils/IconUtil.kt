package com.example.trackme.ui.core.utils

import android.content.Context
import com.maltaisn.icondialog.pack.IconPackLoader
import com.maltaisn.iconpack.defaultpack.createDefaultIconPack

class IconUtil(context : Context) {
    var iconId = 0
    private val iconPackLoader = IconPackLoader(context)
    val iconPack = createDefaultIconPack(iconPackLoader)
    val drawableLoader = iconPackLoader.drawableLoader
}