package mobile.auth.repository

import mobile.auth.model.User


interface UserRepository {
    fun save(user: User): User
    fun get(email: String): User?
}