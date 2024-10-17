package com.example.plugins

import com.example.models.User
import com.example.models.UserSession
import com.example.services.UserService
import io.ktor.server.application.*
import io.ktor.server.freemarker.FreeMarkerContent
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import io.ktor.server.sessions.*

fun Application.configureRouting(userService: UserService) {

    routing {
        staticResources("/static","static")
        get("/login") {
            call.respond(FreeMarkerContent("login.html", null))
        }

        post("/login") {
            val params = call.receiveParameters()
            val email = params["email"]
            val password = params["password"]

            if (userService.loginUser(email!!, password!!)) {
                call.sessions.set(UserSession(email))
                call.respondRedirect("/profile")
            } else {
                call.respond(FreeMarkerContent("login.html", mapOf("error" to "Invalid credentials")))
            }
        }

        get("/register") {
            call.respond(FreeMarkerContent("register.html", null))
        }

        post("/register") {
            val params = call.receiveParameters()
            print(params)
            val user = User(
                username = params["username"]!!,
                password = params["password"]!!,
                age = params["age"]!!.toInt(),
                email = params["email"]!!,
                address = params["address"],
                phone = params["phone"],
                gender = params["gender"]
            )

            if (userService.registerUser(user)) {
                call.respondRedirect("/login")
            } else {
                call.respond(FreeMarkerContent("register.html", mapOf("error" to "User already exists")))
            }
        }

        get("/profile") {
            val session = call.sessions.get<UserSession>()
//            val name = "Sachin"
            val user = userService.getUserDetails(session!!.email)
            print(user)
            call.respond(FreeMarkerContent("profile.html", mapOf("user" to user)))
        }

        post("/profile/update") {
            val session = call.sessions.get<UserSession>()

            if (session == null) {
                // If no session, redirect to login page
                call.respondRedirect("/login")
                return@post
            }

            val currentUser = userService.getUserDetails(session.email)

            val params = call.receiveParameters()
            val updatedUser = currentUser?.copy(
                username = params["username"] ?: currentUser.username,
                age = params["age"]?.toInt() ?: currentUser.age,
                address = params["address"] ?: currentUser.address,
                phone = params["phone"] ?: currentUser.phone,
                gender = params["gender"] ?: currentUser.gender
            )

            if (updatedUser != null) {
                val updateSuccess = userService.updateUserDetails(updatedUser)

                if (updateSuccess) {
                    call.respondRedirect("/profile")
                } else {
                    call.respond(FreeMarkerContent("edit_profile.html", mapOf("user" to updatedUser, "error" to "Update failed. Please try again.")))
                }
            } else {
                call.respond(FreeMarkerContent("edit_profile.html", mapOf("error" to "User not found.")))
            }
        }

        get("/profile/update"){
            val session = call.sessions.get<UserSession>()
            val user = userService.getUserDetails(session!!.email)
            print(user)
            call.respond(FreeMarkerContent("edit_profile.html", mapOf("user" to user)))
        }

        post("/logout") {
            call.sessions.clear<UserSession>()
            call.respondRedirect("/login")
        }

        post("/profile/delete") {
            val session = call.sessions.get<UserSession>()

            if (session == null) {
                call.respondRedirect("/login")
                return@post
            }

            val deleteSuccess = userService.deleteUser(session.email)

            if (deleteSuccess) {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/login")
            } else {
                call.respond(FreeMarkerContent("profile.html", mapOf("error" to "Account deletion failed.")))
            }
        }


    }
}
