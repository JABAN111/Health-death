package mobile.auth.mapper

import migrations.App_user
import mobile.auth.dto.UserDto
import mobile.auth.model.User

fun UserDto.toEntity(): User {
    val user = User()
    user.email = this.email
    user.password = this.password
    return user
}

fun User.toDto(): UserDto {
    return UserDto(this.email, this.password)
}