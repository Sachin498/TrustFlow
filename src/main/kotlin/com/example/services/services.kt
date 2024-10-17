package com.example.services

import com.example.models.User
import com.example.repositories.UserRepository

class UserService(private val userRepository: UserRepository) {

    fun registerUser(user: User): Boolean {
        val existingUser = userRepository.getUserByEmail(user.email)
        if (existingUser != null) return false

        userRepository.addUser(user)
        return true
    }

    fun loginUser(email: String, password: String): Boolean {
        val user = userRepository.getUserByEmail(email)
        return user?.password == password
    }

    fun updateUserDetails(user: User): Boolean {
        userRepository.updateUser(user)
        return true
    }

    fun getUserDetails(email: String): User? {
        return userRepository.getUserByEmail(email)
    }

    fun deleteUser(email: String): Boolean {
        // You may want to implement additional checks or logic here
        userRepository.deleteUser(email)
        return true
    }

}
