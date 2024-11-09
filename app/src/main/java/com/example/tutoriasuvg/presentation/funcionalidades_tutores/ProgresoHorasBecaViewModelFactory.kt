package com.example.tutoriasuvg.presentation.funcionalidades_tutores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProgresoHorasBecaViewModelFactory(
    private val identifier: String,
    private val isUsingCarnet: Boolean
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgresoHorasBecaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgresoHorasBecaViewModel(identifier, isUsingCarnet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
