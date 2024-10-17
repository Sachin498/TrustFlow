package com.example

import com.example.repositories.UserRepository
import com.example.models.User
import kotlin.test.*
import org.junit.jupiter.api.assertThrows
//import com.example.repositories.DuplicateUserException

class DuplicateUserException(message: String) : Exception(message)

class RepositoriesTest {

    private val userRepository = UserRepository()

    @Test
    fun `test save user and retrieve by email`() {
        val user = User(
            username = "Sachin",
            password = "Password123!",
            age = 25,
            email = "Sachin@example.com",
            address = "123 Test St",
            phone = "1234567890",
            gender = "Other"
        )
        userRepository.addUser(user)
        val retrievedUser = userRepository.getUserByEmail("Sachin@example.com")
        assertNotNull(retrievedUser)
        assertEquals(user.email, retrievedUser?.email)
    }

    @Test
    fun `test save user with duplicate email throws exception`() {
        val user = User(
            username = "Sachin",
            password = "Password123!",
            age = 25,
            email = "Sachin@example.com",
            address = "123 Test St",
            phone = "1234567890",
            gender = "Other"
        )
        userRepository.addUser(user)
        assertThrows<DuplicateUserException> {
            userRepository.addUser(user)
        }
    }

    @Test
    fun `test find user by email returns null if not found`() {
        val user = userRepository.getUserByEmail("test123@example.com")
        assertNull(user)
    }

    @Test
    fun `test update user information`() {
        val user = User(
            username = "Sachin",
            password = "Password123!",
            age = 25,
            email = "Sachin@example.com",
            address = "123 Test St",
            phone = "1234567890",
            gender = "Other"
        )
        userRepository.addUser(user)

        // Update password and username
        user.password = "NewPassword1!"
        user.username = "newuser"
        userRepository.updateUser(user)

        val updatedUser = userRepository.getUserByEmail("Sachin@example.com")
        assertNotNull(updatedUser)
        assertEquals("NewPassword1!", updatedUser?.password)
        assertEquals("newuser", updatedUser?.username)
    }

    @Test
    fun `test delete user by email`() {
        val user = User(
            username = "Sachin",
            password = "Password123!",
            age = 25,
            email = "Sachin@example.com",
            address = "123 Test St",
            phone = "1234567890",
            gender = "Other"
        )
        userRepository.addUser(user)
        userRepository.deleteUser(user.email)
        val deletedUser = userRepository.getUserByEmail("test@example.com")
        assertNull(deletedUser)
    }
}
