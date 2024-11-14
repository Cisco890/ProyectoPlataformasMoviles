package com.example.tutoriasuvg.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tutoriaasignada")
data class TutoriaAsignada(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val courseName: String,
    val date: String,
    val time: String,
    val modalidad: String,
    val location: String,
    val tutorId: String? = null
)
