package com.example

import com.example.plugins.*
import com.example.repositories.UserRepository
import com.example.services.UserService
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

//class ApplicationTest {
//    @Test
//    fun testRoot() = testApplication {
//        application {
//            val userRepository = UserRepository()
//            val userService = UserService(userRepository)
//            configureRouting(userService)
//        }
//        client.get("/").apply {
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
//        }
//    }
//}
class ApplicationTest {

    @Test
    fun `test root endpoint`() = testApplication {
        // Setup application
        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }


        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome to Ktor!", bodyAsText())
        }
    }

    @Test
    fun `test login endpoint with valid credentials`() = testApplication {

        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }

        // Perform POST request to login
        val response = client.post("/login") {
            contentType(ContentType.Application.Json)
            setBody("""{"email": "Sachin@example.com", "password": "Password@123"}""")
        }

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Login successful"))
    }

    @Test
    fun `test profile endpoint requires authentication`() = testApplication {

        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }


        val response = client.get("/profile")

        assertEquals(HttpStatusCode.Unauthorized, response.status)
        assertEquals("Unauthorized", response.bodyAsText())
    }

    @Test
    fun `test registration with valid input`() = testApplication {

        application {
            val userRepository = UserRepository()
            val userService = UserService(userRepository)
            configureRouting(userService)
        }


        val response = client.post("/register") {
            contentType(ContentType.Application.Json)
            setBody("""{"email": "Sachin@example.com", "password": "Password123@", "username": "Sachin"}""")
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("User registered successfully", response.bodyAsText())
    }
}

