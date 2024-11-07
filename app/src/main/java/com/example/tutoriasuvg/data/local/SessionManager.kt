package com.example.tutoriasuvg.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private const val TAG = "SessionManager"
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Guarda el tipo de usuario en DataStore.
     */
    suspend fun saveUserType(userType: String, email: String) {
        Log.d(TAG, "Saving user type: $userType with email: $email")
        context.dataStore.edit { preferences ->
            preferences[USER_TYPE_KEY] = userType
            preferences[USER_EMAIL_KEY] = email
            Log.d(TAG, "User type saved in DataStore successfully.")
        }
    }

    /**
     * Borra la sesión y cierra la sesión de Firebase.
     */
    suspend fun clearSession() {
        auth.signOut()
        context.dataStore.edit { preferences ->
            preferences.clear()
            Log.d(TAG, "Session cleared from DataStore and FirebaseAuth.")
        }
    }

    /**
     * Verifica si el usuario está actualmente autenticado en Firebase.
     */
    fun isUserLoggedIn(): Boolean {
        val loggedIn = auth.currentUser != null
        Log.d(TAG, "FirebaseAuth user logged in: $loggedIn")
        return loggedIn
    }

    /**
     * Devuelve un flujo del tipo de usuario almacenado.
     */
    val userType: Flow<String?> = context.dataStore.data
        .map { preferences ->
            val type = preferences[USER_TYPE_KEY]
            Log.d(TAG, "Retrieved user type from DataStore: $type")
            type
        }

    /**
     * Método síncrono para obtener el tipo de usuario desde DataStore.
     */
    suspend fun getUserTypeSync(): String? {
        val type = userType.first()
        Log.d(TAG, "Synchronously retrieved user type from DataStore: $type")
        return type
    }
}
