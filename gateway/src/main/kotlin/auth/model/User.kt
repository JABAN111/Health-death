package mobile.auth.model

import java.time.LocalDateTime

class User() {
    lateinit var email: String
    lateinit var password: String
    val createdAt: LocalDateTime = LocalDateTime.now()
}