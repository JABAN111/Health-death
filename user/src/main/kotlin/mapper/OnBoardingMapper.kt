package mapper

import mobile.user.grpc.PushOnboardingRequest
import model.OnBoardingInfo
import java.sql.Timestamp as SqlTimestamp
import java.time.Instant
import com.google.protobuf.Timestamp as GrpcTimestamp

/**
 * Маппер из grpc-сообщения в нашу доменную модель OnBoardingInfo
 */
fun PushOnboardingRequest.toModel(): OnBoardingInfo {
    val d = this.data

    fun GrpcTimestamp.toSqlTimestamp(): SqlTimestamp =
        SqlTimestamp.from(
            Instant.ofEpochSecond(this.seconds, this.nanos.toLong())
        )

    return OnBoardingInfo(
        sex = d.sex,
        height = d.height.takeIf { it != 0 },
        weight = d.weight.takeIf { it != 0 },
        dateOfBirthday = d.dateOfBirthday.toSqlTimestamp(),
        target = d.target.takeIf { it.isNotBlank() },
        targetWeight = d.targetWeight.takeIf { it != 0 },
        trainsPerWeek = d.trainsPerWeek.takeIf { it != 0 },
        deadlineDate = d.deadlineDate.toSqlTimestamp(),
        dietType = d.dietType.takeIf { it.isNotBlank() },
        breakfastTime = d.breakfastTime.toSqlTimestamp(),
        lunchTime = d.lunchTime.toSqlTimestamp(),
        dinnerTime = d.dinnerTime.toSqlTimestamp()
    )
}