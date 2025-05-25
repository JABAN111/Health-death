package mapper

import mobile.user.grpc.PushOnboardingRequest
import model.OnBoardingInfo
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import com.google.protobuf.Timestamp as GrpcTimestamp

/**
 * Маппер из grpc-сообщения в нашу доменную модель OnBoardingInfo
 */
fun PushOnboardingRequest.toModel(): OnBoardingInfo {
    val d = this.data

    fun GrpcTimestamp.toLocalDateTime(): LocalDateTime =
        LocalDateTime.ofInstant(
            Instant.ofEpochSecond(this.seconds, this.nanos.toLong()),
            ZoneOffset.UTC
        )

    return OnBoardingInfo(
        sex = d.sex,
        height = d.height.takeIf { it != 0 },
        weight = d.weight.takeIf { it != 0 },
        dateOfBirthday = d.dateOfBirthday.toLocalDateTime(),
        target = d.target.takeIf { it.isNotBlank() },
        targetWeight = d.targetWeight.takeIf { it != 0 },
        trainsPerWeek = d.trainsPerWeek.takeIf { it != 0 },
        deadlineDate = d.deadlineDate.toLocalDateTime(),
        dietType = d.dietType.takeIf { it.isNotBlank() },
        breakfastTime = d.breakfastTime.toLocalDateTime(),
        lunchTime = d.lunchTime.toLocalDateTime(),
        dinnerTime = d.dinnerTime.toLocalDateTime()
    )
}