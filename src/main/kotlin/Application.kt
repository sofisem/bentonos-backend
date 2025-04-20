package ru.bentonos

import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import org.jetbrains.exposed.sql.Database
import ru.bentonos.features.login.configureloginRouting
import ru.bentonos.features.orders.configureCreateOrderRouting
import ru.bentonos.features.register.configureRegisterRouting


fun main() {
    Database.connect("jdbc:postgresql://localhost:5432/bentonos", driver = "org.postgresql.Driver", user = "postgres", password = "Txpilt6!")
    embeddedServer(CIO, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureloginRouting()
    configureRegisterRouting()
    configureCreateOrderRouting()
    configureSerialization()
    configureRouting()
}
