package model

import java.time.LocalDateTime

data class User(
    val id: Int,
    val authID: Int,
    val sex: String? = null,
    val birthday: LocalDateTime? = null,
    val dietType: String? = null,
    val breakfastTime: LocalDateTime? = null,
    val lunchTime: LocalDateTime? = null,
    val dinnerTime: LocalDateTime? = null
)