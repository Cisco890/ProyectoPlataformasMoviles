package com.example.tutoriasuvg.data.repository

import android.content.Context
import androidx.room.Room
import com.example.tutoriasuvg.data.database.AppDatabase
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.TutoriaAsignada
import com.example.tutoriasuvg.data.model.Tutor
import kotlinx.coroutines.flow.Flow

class TutoriaRepository(context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "tutoriasuvg-database"
    ).fallbackToDestructiveMigration()
        .build()

    private val solicitudDao = db.solicitudDao()
    private val tutorDao = db.tutorDao()
    private val tutoriaAsignadaDao = db.tutoriaAsignadaDao()

    // Funciones para la tabla de Solicitudes
    suspend fun insertarSolicitud(solicitud: Solicitud) {
        solicitudDao.insertSolicitud(solicitud)
    }

    fun obtenerSolicitudes(): Flow<List<Solicitud>> {
        return solicitudDao.getAllSolicitudes()
    }

    suspend fun asignarTutorASolicitud(solicitudId: Long, tutorId: String) {
        solicitudDao.updateSolicitudTutor(solicitudId, tutorId)
    }

    // Funciones para la tabla de Tutores
    suspend fun insertarTutor(tutor: Tutor) {
        tutorDao.insertTutor(tutor)
    }

    fun obtenerTutores(): Flow<List<Tutor>> {
        return tutorDao.getAllTutores()
    }

    fun obtenerTutoresPorCurso(courseName: String): Flow<List<Tutor>> {
        return tutorDao.getTutoresByCourse(courseName)
    }

    // Funciones para la tabla de Tutorías Asignadas
    suspend fun insertarTutoriaAsignada(tutoriaAsignada: TutoriaAsignada) {
        tutoriaAsignadaDao.insertTutoriaAsignada(tutoriaAsignada)
    }

    fun obtenerTutoriasAsignadas(tutorId: String): Flow<List<TutoriaAsignada>> {
        return tutoriaAsignadaDao.getTutoriaAsignadasByTutorId(tutorId)
    }
}
