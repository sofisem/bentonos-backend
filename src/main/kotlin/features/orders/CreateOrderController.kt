package ru.bentonos.features.orders


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.bentonos.database.orders.OrderDTO
import ru.bentonos.database.orders.OrderItemDTO
import ru.bentonos.database.orders.OrderItems
import ru.bentonos.database.orders.Orders
import java.util.*

class CreateOrderController(private val call: ApplicationCall) {

    suspend fun createOrder() {
        val receive = call.receive<CreateOrderReceiveRemote>()

        val orderId = UUID.randomUUID().toString()
        val createdAt = System.currentTimeMillis().toString()

        val orderDTO = OrderDTO(
            id = orderId,
            userId = receive.userId,
            createdAt = createdAt
        )

        Orders.insert(orderDTO)

        receive.items.forEach { item ->
            val itemDTO = OrderItemDTO(
                id = UUID.randomUUID().toString(),
                orderId = orderId,
                productName = item.productName,
                quantity = item.quantity,
                price = item.price
            )
            OrderItems.insert(itemDTO)
        }

        call.respond(HttpStatusCode.Created, CreateOrderResponseRemote(orderId = orderId))
    }
}
