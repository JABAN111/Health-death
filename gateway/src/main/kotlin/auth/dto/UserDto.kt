package mobile.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val password: String
)
