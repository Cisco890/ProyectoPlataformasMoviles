package com.example.tutoriasuvg.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tutoriasuvg.data.model.Tutor
import kotlinx.coroutines.flow.Flow

@Dao
interface TutorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTutor(tutor: Tutor)

    @Query("SELECT * FROM tutor")
    fun getAllTutores(): Flow<List<Tutor>>

    @Query("SELECT * FROM tutor WHERE id = :id")
    suspend fun getTutorById(id: String): Tutor?

    @Query("SELECT * FROM tutor WHERE :courseName IN (courses)")
    fun getTutoresByCourse(courseName: String): Flow<List<Tutor>>
}
