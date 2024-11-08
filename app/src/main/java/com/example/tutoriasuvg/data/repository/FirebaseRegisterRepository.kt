package com.example.tutoriasuvg.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseRegisterRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun registerUser(email: String, password: String, name: String, userType: String): Result<String> {
        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val userId = auth.currentUser?.uid ?: throw Exception("No se pudo obtener el ID del usuario")

            val userData = mapOf(
                "name" to name,
                "email" to email,
                "userType" to userType
            )

            firestore.collection("users").document(userId).set(userData).await()
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun registerTutorData(userId: String, year: String, hours: String, courses: List<String>): Result<Unit> {
        return try {
            val tutorData = mapOf(
                "year" to year,
                "hours" to hours,
                "courses" to courses,
                "userType" to "tutor"
            )

            // Usar `set` con `SetOptions.merge()` para evitar errores si el documento no existe
            firestore.collection("users").document(userId).set(tutorData, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
