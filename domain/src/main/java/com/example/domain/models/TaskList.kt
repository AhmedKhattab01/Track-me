package com.example.domain.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "lists_table")
data class TaskList(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val creationDate: String,
    var completedTasks : Int = 0,
    var totalTasks : Int = 0,
    var color: Int,
    var iconId : Int,
    var latitude : Double = 0.0,
    var longitude : Double = 0.0,
) : Parcelable
