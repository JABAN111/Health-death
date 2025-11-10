package mobile.auth.repository.impl

import database.UserQueries
import mobile.auth.model.User
import mobile.auth.repository.UserRepository
import mobile.log

class UserRepositoryImpl(
    private val queries: UserQueries
) : UserRepository {

    override fun save(user: User): User {
        queries.insertUser(
            email = user.email,
            password = user.password
        )
        log.debug("save user: {}", user)
        return user
    }

    override fun get(email: String): User? {
        val appUser = queries.getByEmail(email).executeAsOneOrNull() ?: return null

        return User().apply {
            this.email = appUser.email
            this.password = appUser.password
        }
    }
}