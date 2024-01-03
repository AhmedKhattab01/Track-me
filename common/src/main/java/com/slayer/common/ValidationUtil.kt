package com.slayer.common

import android.util.Patterns

object ValidationUtil {
    fun isValidEmailAddress(email : String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}