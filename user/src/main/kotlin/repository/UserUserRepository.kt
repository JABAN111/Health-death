package repository

import model.User
import java.time.LocalDateTime

interface UserUserRepository {

    fun getById(authId: Int): User?
    fun create(authId: Int, sex: String,
               birthday: LocalDateTime?, dietType: String?,
               breakfastTime: LocalDateTime?, lunchTime: LocalDateTime?, dinnerTime:LocalDateTime?)
}