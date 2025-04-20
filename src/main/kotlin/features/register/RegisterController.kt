package ru.bentonos.features.register

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ru.bentonos.database.tokens.TokenDTO
import ru.bentonos.database.tokens.Tokens
import ru.bentonos.database.users.UserDTO
import ru.bentonos.database.users.Users
import ru.bentonos.utils.isValidEmail
import java.util.*

class RegisterController(val call: ApplicationCall) {
    suspend fun registerNewUser(){
        val registerReceiveRemote = call.receive<RegisterReceiveRemote>()
        if (!registerReceiveRemote.email.isValidEmail()) {
            call.respond(HttpStatusCode.BadRequest, "Invalid email")
        }
        val userDTO = Users.fetchUser(registerReceiveRemote.login)
        if(userDTO != null) {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }else{
            val token = UUID.randomUUID().toString()
            val hashedPassword = BCrypt.withDefaults().hashToString(12, registerReceiveRemote.password.toCharArray())

            try {
                Users.insert(
                    UserDTO(
                        login = registerReceiveRemote.login,
                        password = hashedPassword,
                        email= registerReceiveRemote.email,
                        username = ""
                    )
                )

            }catch (e: ExposedSQLException){
                call.respond(HttpStatusCode.BadRequest, "User already exists")
            }

            Tokens.insert(TokenDTO(rowId = UUID.randomUUID().toString(), login = registerReceiveRemote.login, token = token))
            call.respond(RegisterResponseRemote(token = token))
        }


    }
}