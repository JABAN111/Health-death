package mobile.auth.repository.impl

import mobile.auth.model.User
import mobile.auth.repository.UserRepository
import mobile.log

class UserRepositoryImpl : UserRepository {
    private val userData = hashMapOf<String,User>()

    override fun save(user: User): User {
        userData[user.email] = user
        log.debug("save user: {}", user)
        return user
    }

    override fun get(email: String): User? {
        val user = userData[email]
        return user
    }
}