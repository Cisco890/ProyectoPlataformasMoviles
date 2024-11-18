package com.example.tutoriasuvg.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tutoriasuvg.data.model.Tutor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class SessionManager(private val context: Context) {

    companion object {
        private val USER_TYPE_KEY = stringPreferencesKey("user_type")
        private val USER_EMAIL_KEY = stringPreferencesKey("user_email")
        private val USER_IDENTIFIER_KEY = stringPreferencesKey("user_identifier")
        private val IS_USING_CARNET_KEY = booleanPreferencesKey("is_using_carnet")
        private const val TAG = "SessionManager"

        private const val DEFAULT_USER_TYPE = "unknown"
        private const val DEFAULT_EMAIL = ""
        private const val DEFAULT_IDENTIFIER = ""
        private const val DEFAULT_IS_USING_CARNET = true
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun saveUserSession(userType: String, email: String, identifier: String, isUsingCarnet: Boolean) {
        Log.d(TAG, "Saving session: userType=$userType, email=$email, identifier=$identifier, isUsingCarnet=$isUsingCarnet")
        context.dataStore.edit { preferences ->
            preferences[USER_TYPE_KEY] = userType
            preferences[USER_EMAIL_KEY] = email
            preferences[USER_IDENTIFIER_KEY] = identifier
            preferences[IS_USING_CARNET_KEY] = isUsingCarnet
        }
    }

    suspend fun clearSession() {
        auth.signOut()
        context.dataStore.edit { preferences ->
            preferences.clear()
            Log.d(TAG, "Session cleared from DataStore and FirebaseAuth.")
        }
    }

    fun isUserLoggedIn(): Boolean {
        val loggedIn = auth.currentUser != null
        Log.d(TAG, "FirebaseAuth user logged in: $loggedIn")
        return loggedIn
    }

    suspend fun getUserTypeSync(): String {
        val type = context.dataStore.data.map { preferences ->
            preferences[USER_TYPE_KEY] ?: DEFAULT_USER_TYPE
        }.first()
        Log.d(TAG, "Synchronously retrieved user type from DataStore: $type")
        return type
    }

    suspend fun getUserEmailSync(): String {
        val email = context.dataStore.data.map { preferences ->
            preferences[USER_EMAIL_KEY] ?: DEFAULT_EMAIL
        }.first()
        Log.d(TAG, "Synchronously retrieved user email from DataStore: $email")
        return email
    }

    suspend fun getUserIdentifierSync(): String {
        val identifier = context.dataStore.data.map { preferences ->
            preferences[USER_IDENTIFIER_KEY] ?: DEFAULT_IDENTIFIER
        }.first()
        Log.d(TAG, "Synchronously retrieved user identifier from DataStore: $identifier")
        return identifier
    }

    suspend fun isUsingCarnetSync(): Boolean {
        val isCarnet = context.dataStore.data.map { preferences ->
            preferences[IS_USING_CARNET_KEY] ?: DEFAULT_IS_USING_CARNET
        }.first()
        Log.d(TAG, "Synchronously retrieved isUsingCarnet from DataStore: $isCarnet")
        return isCarnet
    }
}
