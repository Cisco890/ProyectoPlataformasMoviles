package com.example.tutoriasuvg.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solicitud")
data class Solicitud(
    @PrimaryKey val id: String = "",
    val courseName: String = "",
    val days: List<String> = emptyList(),
    val shift: String = "",
    val tutorId: String? = null,
    val date: String? = null,
    val location: String? = null,
    val time: String? = null,
    val link: String? = null
) {
    constructor() : this("", "", emptyList(), "")
}
