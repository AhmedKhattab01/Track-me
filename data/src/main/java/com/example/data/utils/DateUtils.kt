package com.example.data.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val currentDate: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
