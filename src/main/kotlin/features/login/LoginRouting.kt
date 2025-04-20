package ru.bentonos.features.login
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureloginRouting() {
    routing {
        post("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}
