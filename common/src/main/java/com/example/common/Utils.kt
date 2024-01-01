package com.example.common

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

object Utils {
    fun convertTimestampToFriendlyDate(timestamp: Long): String {
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - timestamp

        val seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime)
        val hours = TimeUnit.MILLISECONDS.toHours(elapsedTime)
        val days = TimeUnit.MILLISECONDS.toDays(elapsedTime)

        return when {
            seconds < 60 -> "Just now"
            minutes < 60 -> "$minutes minute${if (minutes > 1) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
            else -> {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.US)
                dateFormat.format(Date(timestamp))
            }
        }
    }
}