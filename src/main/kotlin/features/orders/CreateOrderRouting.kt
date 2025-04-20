package ru.bentonos.features.orders

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureCreateOrderRouting() {
    routing {
        post("/order/create") {
            val controller = CreateOrderController(call)
            controller.createOrder()
        }
    }
}