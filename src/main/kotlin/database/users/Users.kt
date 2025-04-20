package ru.bentonos.database.users

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Users: Table("users") {
    private val login = varchar("login", 25)
    private val password = varchar("password", 100)
    private val username = varchar("username", 30)
    private val email = varchar("email", 25)

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[login] = userDTO.login
                it[password] = userDTO.password
                it[username] = userDTO.username
                it[email] = userDTO.email?:""
            }
        }
    }
    fun fetchUser(login: String): UserDTO? {git --version

            return try {
            transaction {
                val userModel = Users.select{ Users.login.eq(login)}.single()
                UserDTO(
                    login = userModel[Users.login],
                    password = userModel[Users.password],
                    username = userModel[Users.username],
                    email = userModel[Users.email]
                )
            }

        } catch (e: Exception) {
            null
        }
    }
}