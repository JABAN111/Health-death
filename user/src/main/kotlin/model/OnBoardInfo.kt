package model

import java.sql.Timestamp

// NOTE: name info chosen in order to escape confusing with grpc OnBoardingData
data class OnBoardingInfo(
    val sex: String,
    val height: Int? = null,
    val weight: Int? = null,
    val dateOfBirthday: Timestamp? = null,
    val target: String? = null,
    val targetWeight: Int? = null,
    val trainsPerWeek: Int? = null,
    val deadlineDate: Timestamp? = null,
    val dietType: String? = null,
    val breakfastTime: Timestamp? = null,
    val lunchTime: Timestamp? = null,
    val dinnerTime: Timestamp? = null
)