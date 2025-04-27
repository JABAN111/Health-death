package mobile.auth.service.impl

import mobile.auth.dto.UserDto
import mobile.auth.mapper.UserMapper
import mobile.auth.repository.UserRepository
import mobile.auth.service.UserService


class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {
    override fun save(userDto: UserDto): UserDto {
        val userEntity = UserMapper.toEntity(userDto)
        return UserMapper.toDto(userRepository.save(userEntity))
    }

    override fun get(email: String): UserDto {
        val user = userRepository.get(email)
        return UserMapper.toDto(user)
    }
}