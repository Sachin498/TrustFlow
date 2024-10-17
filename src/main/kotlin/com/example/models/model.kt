package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var username: String,
    var password: String,
    val age: Int,
    val email: String,
    var address: String?,
    var phone: String?,
    var gender: String?
)

@Serializable
data class UserSession(val email: String)