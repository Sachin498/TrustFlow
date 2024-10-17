package com.example.repositories

import com.example.models.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {
    object Users : Table() {
        val username = varchar("username", 50)
        val password = varchar("password", 64)
        val age = integer("age")
        val email = varchar("email", 100)
        val address = varchar("address", 255).nullable()
        val phone = varchar("phone", 15).nullable()
        val gender = varchar("gender", 20).nullable()

        override val primaryKey = PrimaryKey(email) // Set the primary key here
    }

    fun addUser(user: User) = transaction {
        Users.insert {
            it[username] = user.username
            it[password] = user.password
            it[age] = user.age
            it[email] = user.email
            it[address] = user.address
            it[phone] = user.phone
            it[gender] = user.gender
        }
    }

    fun getUserByEmail(email: String): User? = transaction {
        Users.select { Users.email eq email }
            .map { rowToUser(it) }
            .singleOrNull()
    }

//    fun getUserByUsername(username: String): User? = transaction {
//        Users.select { Users.username eq username }
//            .map { rowToUser(it) }
//            .singleOrNull()
//    }

    fun updateUser(user: User) = transaction {
        Users.update({ Users.email eq user.email }) {
            it[password] = user.password
            it[age] = user.age
            it[username] = user.username
            it[address] = user.address
            it[phone] = user.phone
            it[gender] = user.gender
        }
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            username = row[Users.username],
            password = row[Users.password],
            age = row[Users.age],
            email = row[Users.email],
            address = row[Users.address],
            phone = row[Users.phone],
            gender = row[Users.gender]
        )
    }

    fun deleteUser(email: String) = transaction {
        Users.deleteWhere { Users.email eq email }
    }

}
