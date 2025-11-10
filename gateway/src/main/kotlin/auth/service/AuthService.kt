package mobile.auth.service

import mobile.auth.dto.JwtResponse
import mobile.auth.dto.UserDto

interface AuthService {
    fun signUp(userDto: UserDto): JwtResponse
    fun signIn(userDto: UserDto): JwtResponse
}