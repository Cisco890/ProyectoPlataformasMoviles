package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.local.SessionManager
import com.example.tutoriasuvg.data.repository.FirebaseLoginRepository
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore

class PerfilTutorViewModelFactory(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val crashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance(),
    private val loginRepository: FirebaseLoginRepository = FirebaseLoginRepository(),
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilTutorViewModel::class.java)) {
            return PerfilTutorViewModel(
                firestore = firestore,
                crashlytics = crashlytics,
                loginRepository = loginRepository,
                sessionManager = sessionManager
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
