package com.example.common_ui

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

object Utils {
    fun Fragment.toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun Fragment.toast(@StringRes message: Int) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    fun Activity.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Activity.toast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun View.gone() = run { visibility = View.GONE }

    fun View.visible() = run { visibility = View.VISIBLE }

    fun View.invisible() = run { visibility = View.INVISIBLE }

    infix fun View.visibleIf(condition: Boolean) =
        run { visibility = if (condition) View.VISIBLE else View.GONE }

    infix fun View.goneIf(condition: Boolean) =
        run { visibility = if (condition) View.GONE else View.VISIBLE }

    infix fun View.invisibleIf(condition: Boolean) =
        run { visibility = if (condition) View.INVISIBLE else View.VISIBLE }
}