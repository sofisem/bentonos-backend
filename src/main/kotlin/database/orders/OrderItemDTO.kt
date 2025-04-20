package ru.bentonos.database.orders

data class OrderItemDTO(
    val id: String,
    val orderId: String,
    val productName: String,
    val quantity: String,
    val price: String
)
