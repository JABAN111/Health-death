package model

import java.sql.Timestamp
import java.time.LocalDateTime

// NOTE: name info chosen in order to escape confusing with grpc OnBoardingData
data class OnBoardingInfo(
    val sex: String,
    val height: Int? = null,
    val weight: Int? = null,
    val dateOfBirthday: LocalDateTime? = null,
    val target: String? = null,
    val targetWeight: Int? = null,
    val trainsPerWeek: Int? = null,
    val deadlineDate: LocalDateTime? = null,
    val dietType: String? = null,
    val breakfastTime: LocalDateTime? = null,
    val lunchTime: LocalDateTime? = null,
    val dinnerTime: LocalDateTime? = null
)