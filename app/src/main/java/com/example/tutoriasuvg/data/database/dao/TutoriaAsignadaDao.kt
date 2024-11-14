package com.example.tutoriasuvg.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tutoriasuvg.data.model.TutoriaAsignada
import kotlinx.coroutines.flow.Flow

@Dao
interface TutoriaAsignadaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutoriaAsignada(tutoriaAsignada: TutoriaAsignada)

    @Update
    suspend fun updateTutoriaAsignada(tutoriaAsignada: TutoriaAsignada)

    @Query("SELECT * FROM tutoriaasignada")
    fun getAllTutoriaAsignadas(): Flow<List<TutoriaAsignada>>

    @Query("SELECT * FROM tutoriaasignada WHERE tutorId = :tutorId")
    fun getTutoriaAsignadasByTutorId(tutorId: String): Flow<List<TutoriaAsignada>>
}
