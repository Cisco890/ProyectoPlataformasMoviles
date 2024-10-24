package com.example.tutoriasuvg.domain.repository

import com.example.tutoriasuvg.data.local.entity.User

interface UserRepository {
    suspend fun insertUser(user: User)
    suspend fun loginUser(email: String, password: String): User?
}