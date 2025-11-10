package mobile.auth.service.impl

import mobile.auth.dto.UserDto
import mobile.auth.mapper.toDto
import mobile.auth.mapper.toEntity
import mobile.auth.repository.UserRepository
import mobile.auth.service.UserService


class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun save(userDto: UserDto): UserDto {
        val userEntity = userDto.toEntity()
        return userRepository.save(userEntity).toDto()
    }

    override fun get(email: String): UserDto? {
        val user = userRepository.get(email)
        return user?.toDto()
    }
}