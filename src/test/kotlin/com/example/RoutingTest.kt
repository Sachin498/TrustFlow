package com.example
import com.example.plugins.*
import com.example.repositories.UserRepository
import com.example.services.UserService
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class RoutingTest {


    @Test
    fun `test root endpoint returns expected response`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello worlf", bodyAsText())
        }
    }

    @Test
    fun `test login with valid credentials returns success`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.post("/login") {
            setBody("""{"email": "Sachin@example.com", "password": "Password1!"}""")
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Login successful", bodyAsText())
        }
    }

    @Test
    fun `test login with invalid password returns error`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.post("/login") {
            setBody("""{"email": "Sachin@example.com", "password": "WrongPassword12!"}""")
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
            assertEquals("Invalid credentials", bodyAsText())
        }
    }

    @Test
    fun `test login with missing fields returns error`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.post("/login") {
            setBody("""{"email": "Sachin@example.com"}""")
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals("Missing required fields", bodyAsText())
        }
    }

    @Test
    fun `test register with valid data returns success`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.post("/register") {
            setBody("""{"email": "Maverick@example.com", "password": "Password@12!"}""")
            contentType(ContentType.Application.Json)
        }.apply {
            assertEquals(HttpStatusCode.Created, status)
            assertEquals("Registration successful", bodyAsText())
        }
    }

    @Test
    fun `test profile endpoint returns unauthorized without login`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        client.get("/profile").apply {
            assertEquals(HttpStatusCode.Unauthorized, status)
        }
    }

    @Test
    fun `test profile endpoint returns user profile after login`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        val loginResponse = client.post("/login") {
            setBody("""{"email": "Sachin@example.com", "password": "Password12!"}""")
            contentType(ContentType.Application.Json)
        }
        val sessionCookie = loginResponse.headers[HttpHeaders.SetCookie]?.substringBefore(";")

        client.get("/profile") {
            header(HttpHeaders.Cookie, sessionCookie)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertTrue(bodyAsText().contains("Profile of valid@example.com"))
        }
    }

    @Test
    fun `test delete account deletes user after login`() = testApplication {
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }
        val loginResponse = client.post("/login") {
            setBody("""{"email": "Sachin@example.com", "password": "Password1!"}""")
            contentType(ContentType.Application.Json)
        }
        val sessionCookie = loginResponse.headers[HttpHeaders.SetCookie]?.substringBefore(";")

        client.delete("/account") {
            header(HttpHeaders.Cookie, sessionCookie)
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Account deleted", bodyAsText())
        }
    }
}

