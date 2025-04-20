package ru.bentonos.features.login

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.bentonos.database.tokens.TokenDTO
import ru.bentonos.database.tokens.Tokens
import ru.bentonos.database.users.Users
import java.util.*

class LoginController(private val call: ApplicationCall) {
    suspend fun performLogin(){

        val receive = call.receive<LoginReceiveRemote>()
        val userDTO = Users.fetchUser(receive.login)

        if (userDTO == null) {
            call.respond(HttpStatusCode.BadRequest, "User not found")
        }else{

            val result = BCrypt.verifyer().verify(receive.password.toCharArray(), userDTO.password)

            if (result.verified) {
                // Если пароль верный, создаём токен и сохраняем в базе
                val token = UUID.randomUUID().toString()
                Tokens.insert(TokenDTO(rowId = UUID.randomUUID().toString(), login = receive.login, token = token))
                // Отправляем токен обратно клиенту
                call.respond(LoginResponseRemote(token = token))
            } else {
                // Если пароль неверный
                call.respond(HttpStatusCode.BadRequest, "Invalid Password")
            }
        }
    }
}