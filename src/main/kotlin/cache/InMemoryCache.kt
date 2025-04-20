package ru.bentonos.cache
import ru.bentonos.features.register.RegisterReceiveRemote

data class TokenCache(
    val login: String,
    val token: String
)
object InMemoryCache {
    val userList: MutableList<RegisterReceiveRemote> = mutableListOf()
    val token: MutableList<TokenCache> = mutableListOf()
}