package com.example.tutoriasuvg.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseLoginRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val userId = auth.currentUser?.uid ?: throw Exception("Usuario no encontrado")
            Result.success(userId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserType(userId: String): Result<String> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                val userType = document.getString("userType") ?: throw Exception("Tipo de usuario no encontrado")
                Result.success(userType)
            } else {
                Result.failure(Exception("Usuario no encontrado en Firestore"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }

    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    suspend fun getUserData(userId: String): Result<Map<String, Any>> {
        return try {
            val document = firestore.collection("users").document(userId).get().await()
            if (document.exists()) {
                Result.success(document.data ?: emptyMap())
            } else {
                Result.failure(Exception("Usuario no encontrado"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateUserType(userId: String, userType: String): Result<Unit> {
        return try {
            firestore.collection("users").document(userId).update("userType", userType).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
