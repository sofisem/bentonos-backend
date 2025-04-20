package ru.bentonos.features.orders

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderReceiveRemote(
    val userId: String,
    val items: List<OrderItemReceiveRemote>
)

@Serializable
data class OrderItemReceiveRemote(
    val productName: String,
    val quantity: String,
    val price: String
)

@Serializable
data class CreateOrderResponseRemote(
    val orderId: String
)