package com.example.tutoriasuvg.data.repository

import android.content.Context
import androidx.room.Room
import com.example.tutoriasuvg.data.database.AppDatabase
import com.example.tutoriasuvg.data.model.Solicitud
import com.example.tutoriasuvg.data.model.TutoriaAsignada
import com.example.tutoriasuvg.data.model.Tutor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

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

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun insertarSolicitud(solicitud: Solicitud) {
        solicitudDao.insertSolicitud(solicitud)
    }

    fun obtenerSolicitudes(): Flow<List<Solicitud>> {
        return solicitudDao.getAllSolicitudes()
    }

    suspend fun asignarTutorASolicitud(solicitudId: Long, tutorId: String) {
        solicitudDao.updateSolicitudTutor(solicitudId, tutorId)
    }

    suspend fun eliminarSolicitud(solicitudId: String) {
        solicitudDao.eliminarSolicitud(solicitudId)
    }

    private suspend fun eliminarSolicitudDeFirestore(solicitudId: String) {
        try {
            firestore.collection("solicitudes").document(solicitudId).delete().await()
        } catch (e: Exception) {
            println("Error al eliminar la solicitud de Firestore: ${e.message}")
            throw e
        }
    }

    suspend fun insertarTutor(tutor: Tutor) {
        tutorDao.insertTutor(tutor)
    }

    fun obtenerTutores(): Flow<List<Tutor>> {
        return tutorDao.getAllTutores()
    }

    fun obtenerTutoresPorCurso(courseName: String): Flow<List<Tutor>> {
        return tutorDao.getTutoresByCourse(courseName)
    }

    suspend fun incrementarProgresoTutor(tutorId: String, incremento: Float) {
        val tutor = tutorDao.getTutorById(tutorId)
        if (tutor != null) {
            val tutorActualizado = tutor.copy(
                completedHours = (tutor.completedHours ?: 0f) + incremento
            )
            tutorDao.insertTutor(tutorActualizado)
        }
    }

    suspend fun insertarTutoriaAsignada(tutoriaAsignada: TutoriaAsignada) {
        tutoriaAsignadaDao.insertTutoriaAsignada(tutoriaAsignada)
    }

    fun obtenerTutoriasAsignadas(tutorId: String): Flow<List<TutoriaAsignada>> {
        return tutoriaAsignadaDao.getTutoriaAsignadasByTutorId(tutorId)
    }

    suspend fun eliminarTutoriaAsignada(tutoriaAsignadaId: Int) {
        tutoriaAsignadaDao.eliminarTutoriaAsignada(tutoriaAsignadaId)
    }

    suspend fun completarTutoria(
        solicitudId: String,
        tutoriaAsignadaId: Int,
        tutorId: String,
        incrementoHoras: Float = 1.3f
    ) {
        try {
            println("Inicio de completarTutoria en el repositorio")

            incrementarProgresoTutor(tutorId, incrementoHoras)
            println("Horas incrementadas para el tutor $tutorId con $incrementoHoras horas")

            eliminarSolicitud(solicitudId)
            println("Solicitud con ID $solicitudId eliminada de la base de datos local")

            eliminarTutoriaAsignada(tutoriaAsignadaId)
            println("Tutoría asignada con ID $tutoriaAsignadaId eliminada de la base de datos local")
        } catch (e: Exception) {
            println("Error en el repositorio: ${e.message}")
            throw e
        }
    }


}
