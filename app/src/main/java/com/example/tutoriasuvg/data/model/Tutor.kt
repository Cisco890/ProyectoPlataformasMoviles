package com.example.tutoriasuvg.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tutor")
data class Tutor(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val courses: List<String> = emptyList(),
    val completedHours: Float = 0f
)
