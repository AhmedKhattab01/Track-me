package com.example.trackme.ui.shared_viewmodels

import androidx.lifecycle.ViewModel
import com.example.trackme.ui.core.utils.IconUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class IconViewModel @Inject constructor(
    private val iconUtil: IconUtil
) : ViewModel() {
    var iconId = iconUtil.iconId
    val iconPack = iconUtil.iconPack
    val drawableLoader = iconUtil.drawableLoader
}