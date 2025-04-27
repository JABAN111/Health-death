package mobile.auth.service

import mobile.auth.dto.UserDto

interface UserService {
    fun save(userDto: UserDto): UserDto
    fun get(email: String): UserDto?
}