package com.example.tutoriasuvg.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FirebaseRegisterRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    // Función para registrar un nuevo usuario
    suspend fun registerUser(
        email: String,
        password: String,
        name: String,
        userType: String,
        carnet: String,
        year: String
    ): Result<String> {
        require(email.isNotBlank() && password.isNotBlank()) { "Email y contraseña no pueden estar vacíos." }
        require(name.isNotBlank() && userType.isNotBlank() && carnet.isNotBlank() && year.isNotBlank()) {
            "Todos los campos deben estar completos."
        }

        return try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val userId = auth.currentUser?.uid ?: throw Exception("Error al obtener ID del usuario después de registrar")

            val userData = mapOf(
                "name" to name,
                "email" to email,
                "userType" to userType,
                "carnet" to carnet,
                "year" to year
            )

            firestore.collection("users").document(userId).set(userData, SetOptions.merge()).await()
            Result.success(userId)
        } catch (e: FirebaseAuthException) {
            Result.failure(Exception("Error de autenticación: ${e.message}", e))
        } catch (e: FirebaseFirestoreException) {
            Result.failure(Exception("Error de Firestore: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Función para registrar los datos de un tutor
    suspend fun registerTutorData(
        userId: String,
        hours: Int,
        courses: List<String>
    ): Result<Unit> {
        require(userId.isNotBlank()) { "ID de usuario no puede estar vacío." }
        require(hours >= 0) { "Horas completadas deben ser mayor o igual a 0." }
        require(courses.isNotEmpty()) { "Debe proporcionar al menos un curso." }

        return try {
            val tutorData = mapOf(
                "hours" to hours,
                "courses" to courses,
                "userType" to "tutor"
            )

            firestore.collection("users").document(userId).set(tutorData, SetOptions.merge()).await()
            Result.success(Unit)
        } catch (e: FirebaseFirestoreException) {
            Result.failure(Exception("Error al guardar datos del tutor en Firestore: ${e.message}", e))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
