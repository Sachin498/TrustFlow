package com.example

import com.example.plugins.*
import com.example.repositories.UserRepository
import com.example.services.UserService
import com.example.models.UserSession
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import freemarker.cache.ClassTemplateLoader
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import io.ktor.server.sessions.*




fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Install session handling
    install(Sessions) {
        cookie<UserSession>("user_session") {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.extensions["SameSite"] = "lax"
        }
    }


    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }

    initDB()

    val userRepository = UserRepository()
    val userService = UserService(userRepository)

    configureRouting(userService)
}

fun initDB() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:mysql://localhost:3306/trust"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = "root"  // Change accorgingly
        password = "Maverick@123"  // Change this to your MySQL password
        maximumPoolSize = 3
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }
    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    // Create Users table if it doesn't exist
    transaction {
        SchemaUtils.create(UserRepository.Users)
    }
}
