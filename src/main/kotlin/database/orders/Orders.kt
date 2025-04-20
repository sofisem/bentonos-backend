package ru.bentonos.database.orders



import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Orders : Table("orders") {
    private val id = varchar("id", 50)
    private val userId = varchar("user_id", 50)
    private val createdAt = varchar("created_at", 50)

    fun insert(orderDTO: OrderDTO) {
        transaction {
            Orders.insert {
                it[id] = orderDTO.id
                it[userId] = orderDTO.userId
                it[createdAt] = orderDTO.createdAt
            }
        }
    }

    fun fetchOrdersByUser(userId: String): List<OrderDTO> {
        return transaction {
            Orders.select { Orders.userId eq userId }.map {
                OrderDTO(
                    id = it[Orders.id],
                    userId = it[Orders.userId],
                    createdAt = it[Orders.createdAt]
                )
            }
        }
    }
}
