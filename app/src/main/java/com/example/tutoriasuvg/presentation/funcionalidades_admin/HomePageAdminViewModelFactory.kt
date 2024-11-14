package com.example.tutoriasuvg.presentation.funcionalidades_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutoriasuvg.data.repository.TutoriasRepository

class HomePageAdminViewModelFactory(
    private val tutoriasRepository: TutoriasRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomePageAdminViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomePageAdminViewModel(tutoriasRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
