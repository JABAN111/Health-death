package mobile.auth.mapper

import mobile.auth.dto.UserDto
import mobile.auth.model.User

class UserMapper {
    companion object {
        fun toEntity(dto: UserDto): User {
            val user = User()
            user.email = dto.email
            user.password = dto.password
            return user
        }

        fun toDto(user: User): UserDto {
            return UserDto(user.email, user.password)
        }
    }
}