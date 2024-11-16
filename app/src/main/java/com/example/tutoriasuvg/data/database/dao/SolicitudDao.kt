package com.example.tutoriasuvg.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tutoriasuvg.data.model.Solicitud
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSolicitud(solicitud: Solicitud)

    @Query("UPDATE solicitud SET tutorId = :tutorId WHERE id = :solicitudId")
    suspend fun updateSolicitudTutor(solicitudId: Long, tutorId: String)

    @Query("SELECT * FROM solicitud")
    fun getAllSolicitudes(): Flow<List<Solicitud>>

    @Query("SELECT * FROM solicitud WHERE tutorId = :tutorId")
    fun getSolicitudesByTutorId(tutorId: String): Flow<List<Solicitud>>

    @Query("DELETE FROM solicitud WHERE id = :solicitudId")
    suspend fun eliminarSolicitud(solicitudId: String)
}

