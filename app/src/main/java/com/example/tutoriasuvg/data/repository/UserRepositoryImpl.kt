package com.example.tutoriasuvg.data.repository

import com.example.tutoriasuvg.data.local.dao.UserDao
import com.example.tutoriasuvg.data.local.entity.User
import com.example.tutoriasuvg.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao) : UserRepository {

    override suspend fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    override suspend fun loginUserByEmail(email: String): User? {
        return userDao.loginUserByEmail(email)
    }
}
