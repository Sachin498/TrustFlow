package com.example


import kotlin.test.*
import com.example.models.User
import com.example.repositories.UserRepository
import com.example.services.UserService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class UserServiceIntegrationTest {

    private lateinit var userService: UserService
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setUp() {
        userRepository = UserRepository()
        userService = UserService(userRepository)


    }

    @Test
    fun `test user registration and retrieval`() {
        val user = User(username = "testUser", password = "Password1!", email = "test@example.com", age = 30, address = "address", gender="Male", phone="9999999999")


        userService.registerUser(user)

        val retrievedUser = userRepository.getUserByEmail(user.email)
        assertNotNull(retrievedUser)
        assertEquals(user.username, retrievedUser?.username)
    }

    @Test
    fun `test user registration fails for duplicate email`() {

        val user = User(username = "testUser1", password = "Password1!", email = "duplicate@example.com", age = 30, address = "address", gender="Male", phone="9999999999")
        userService.registerUser(user)


        val result = assertFailsWith<DuplicateUserException> {
            userService.registerUser(user)
        }


        assertEquals("Email already in use", result.message)
    }

    @Test
    fun `test user login with valid credentials`() {

        val user = User(username = "testUser2", password = "Password1!", email = "login@example.com", age = 30, address = "address", gender="Male", phone="9999999999")
        userService.registerUser(user)


        val loginResult = userService.loginUser(user.email, user.password)


        assertTrue(loginResult)

    }

    @Test
    fun `test user login fails with invalid password`() {

        val user = User(username = "testUser3", password = "Password1!", email = "invalid@example.com", age = 30, address = "address", gender="Male", phone="9999999999")
        userService.registerUser(user)


        val loginResult = userService.loginUser(user.email, "WrongPassword!")


        assertFalse(loginResult)

    }

    @Test
    fun `test update user information`() {
        val user = User(username = "testUser4", password = "Password1!", email = "update@example.com", age = 30, address = "address", gender="Male", phone="9999999999")
        userService.registerUser(user)

        user.username = "updatedUser"
        userService.updateUserDetails(user)

        val updatedUser = userRepository.getUserByEmail(user.email)
        assertNotNull(updatedUser)
        assertEquals("updatedUser", updatedUser?.username)
    }

    @Test
    fun `test delete user account`() {

        val user = User(username = "testUser5", password = "Password1!", email = "delete@example.com", age = 30, address = "address", gender="Male", phone="9999999999")
        userService.registerUser(user)


        userService.deleteUser(user.email)


        val deletedUser = userRepository.getUserByEmail(user.email)
        assertNull(deletedUser)
    }


}
