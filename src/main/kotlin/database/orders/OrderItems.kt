package ru.bentonos.database.orders


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object OrderItems : Table("order_items") {
    private val id = varchar("id", 50)
    private val orderId = varchar("order_id", 50)
    private val productName = varchar("product_name", 100)
    private val quantity = varchar("quantity", 50)
    private val price = varchar("price", 50)

    fun insert(itemDTO: OrderItemDTO) {
        transaction {
            OrderItems.insert {
                it[id] = itemDTO.id
                it[orderId] = itemDTO.orderId
                it[productName] = itemDTO.productName
                it[quantity] = itemDTO.quantity
                it[price] = itemDTO.price
            }
        }
    }

    fun fetchItemsByOrder(orderId: String): List<OrderItemDTO> {
        return transaction {
            OrderItems.select { OrderItems.orderId eq orderId }.map {
                OrderItemDTO(
                    id = it[OrderItems.id],
                    orderId = it[OrderItems.orderId],
                    productName = it[OrderItems.productName],
                    quantity = it[OrderItems.quantity],
                    price = it[OrderItems.price]
                )
            }
        }
    }
}
