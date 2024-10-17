package com.example
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class AuthService {

    private val users = mutableMapOf<String, String>()

    fun register(email: String, password: String): AuthResult {
        return if (users.containsKey(email)) {
            AuthResult(success = false, message = "Email already in use")
        } else {
            users[email] = password
            AuthResult(success = true, message = "Registration successful")
        }
    }

    fun login(email: String, password: String): AuthResult {
        return if (users[email] == password) {
            AuthResult(success = true, message = "Login successful")
        } else {
            AuthResult(success = false, message = "Invalid credentials")
        }
    }

    fun deleteAccount(email: String): AuthResult {
        return if (users.remove(email) != null) {
            AuthResult(success = true, message = "Account deleted successfully")
        } else {
            AuthResult(success = false, message = "Account not found")
        }
    }
}

data class AuthResult(val success: Boolean, val message: String)

class ServicesTest {

    private val authService = AuthService()

    @Test
    fun `test register with valid credentials`() {
        val result = authService.register("Sachin@example.com", "Password1!")
        assertTrue(result.success)
        assertEquals("Registration successful", result.message)
    }

    @Test
    fun `test register with existing email fails`() {
        authService.register("Sachin@example.com", "Password1!")
        val result = authService.register("Sachin@example.com", "Password1!")
        assertFalse(result.success)
        assertEquals("Email already in use", result.message)
    }

    @Test
    fun `test login with correct credentials`() {
        authService.register("Sachin@example.com", "Password1!")
        val result = authService.login("Sachin@example.com", "Password1!")
        assertTrue(result.success)
        assertEquals("Login successful", result.message)
    }

    @Test
    fun `test login with incorrect password fails`() {
        authService.register("Sachin@example.com", "Password1!")
        val result = authService.login("Sachin@example.com", "WrongPassword!")
        assertFalse(result.success)
        assertEquals("Invalid credentials", result.message)
    }

    @Test
    fun `test delete account`() {
        authService.register("Sachin@example.com", "Password1!")
        val result = authService.deleteAccount("Sachin@example.com")
        assertTrue(result.success)
        assertEquals("Account deleted successfully", result.message)
    }

    @Test
    fun `test delete account for non-existent user returns error`() {
        val result = authService.deleteAccount("Sachin123@example.com")
        assertFalse(result.success)
        assertEquals("Account not found", result.message)
    }
}

