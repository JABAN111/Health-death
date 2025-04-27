package mobile.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class JwtResponse(
    val token: String
)
