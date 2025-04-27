package mobile.auth.service.impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.util.logging.KtorSimpleLogger
import mobile.auth.dto.JwtResponse
import mobile.auth.dto.UserDto
import mobile.auth.exception.AuthFailedException
import mobile.auth.exception.UserNotExistException
import mobile.auth.service.AuthService
import mobile.auth.service.UserService
import org.mindrot.jbcrypt.BCrypt
import java.util.*

private val log = KtorSimpleLogger("mobile.gateway.ApplicationKt")

class AuthServiceImpl(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
    private val userService: UserService
) : AuthService {

    override fun signUp(userDto: UserDto): JwtResponse {
        val hashedPwd = hashPwd(userDto.password)
        val userToSave = UserDto(userDto.email, hashedPwd)

        val savedUser = userService.save(userToSave)
        return generateJwtResponse(savedUser.email)
    }

    override fun signIn(userDto: UserDto): JwtResponse {
        val user = try {
            userService.get(userDto.email)
        } catch (e: NullPointerException) {
            throw UserNotExistException("Пользователь с email: '${userDto.email}' не найден")
        }

        if (!BCrypt.checkpw(userDto.password, user.password)) {
            log.warn("User with email {} entered invalid password", user.email)
            throw AuthFailedException("Неверный email или пароль")
        }

        return generateJwtResponse(user.email)
    }

    private fun generateJwtResponse(email: String): JwtResponse {
        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", email)
            .withExpiresAt(Date(System.currentTimeMillis() + 60_000))
            .sign(Algorithm.HMAC256(secret))
        return JwtResponse(token)
    }

    private fun hashPwd(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())
}
