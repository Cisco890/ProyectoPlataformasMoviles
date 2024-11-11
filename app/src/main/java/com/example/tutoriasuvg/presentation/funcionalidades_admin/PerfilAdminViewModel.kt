package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilAdminViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _adminName = MutableStateFlow<String?>(null)
    val adminName: StateFlow<String?> get() = _adminName

    private val _logoutEvent = MutableStateFlow(false)
    val logoutEvent: StateFlow<Boolean> get() = _logoutEvent

    init {
        fetchAdminData()
    }

    private fun fetchAdminData() {
        viewModelScope.launch {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                firestore.collection("admins")
                    .document(currentUser.uid)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            _adminName.value = document.getString("name") ?: "Nombre no disponible"
                        } else {
                            _adminName.value = "Administrador no encontrado"
                        }
                    }
                    .addOnFailureListener {
                        _adminName.value = "Error al cargar el nombre"
                    }
            } else {
                _adminName.value = "Usuario no autenticado"
            }
        }
    }

    fun logout() {
        auth.signOut()
        _logoutEvent.value = true
    }
}
