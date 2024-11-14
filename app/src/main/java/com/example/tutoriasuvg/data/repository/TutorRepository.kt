package com.example.tutoriasuvg.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.tutoriasuvg.data.model.Tutor
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class TutorRepository {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _tutores = MutableStateFlow<List<Tutor>>(
        listOf(
            Tutor(id = "1", name = "Tutor 1", courses = listOf("Ecuaciones diferenciales I", "Física 3")),
            Tutor(id = "2", name = "Tutor 2", courses = listOf("Química General", "Matemática Discreta")),
            Tutor(id = "3", name = "Tutor 3", courses = listOf("Programación Orientada a Objetos", "Cálculo 2"))
        )
    )
    val tutores: StateFlow<List<Tutor>> = _tutores

    suspend fun getTutoresPorCurso(courseName: String): Result<List<Tutor>> {
        return try {
            val snapshot = firestore.collection("users")
                .whereEqualTo("userType", "tutor")
                .whereArrayContains("courses", courseName)
                .get()
                .await()

            val tutores = snapshot.documents.mapNotNull { document ->
                document.toObject(Tutor::class.java)?.copy(id = document.id)
            }

            Log.d(TAG, "Tutores retrieved for course $courseName: ${tutores.size} found.")
            Result.success(tutores)
        } catch (e: FirebaseFirestoreException) {
            Log.e(TAG, "Error retrieving tutors for course $courseName: ${e.message}", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error retrieving tutors for course $courseName: ${e.message}", e)
            Result.failure(e)
        }
    }
}

