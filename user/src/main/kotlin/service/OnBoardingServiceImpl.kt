package service

import model.OnBoardingInfo
import repository.BodyParametersCurrentRepository
import repository.BodyParametersGoalRepository
import repository.TrainGoalsRepository
import repository.UserUserRepository

/**
 * Сервис, который будет добавлять, обновлять в бд информацию о пользовательских данных
 */
class OnBoardingServiceImpl(
    private val bodyParametersGoalRepository: BodyParametersGoalRepository,
    private val bodyParametersCurrentRepository: BodyParametersCurrentRepository,
    private val trainGoalsRepository: TrainGoalsRepository,
    private val userUserRepository: UserUserRepository
): OnBoardingService {
    /**
     * Создает запись в хранилище, в случае провала выкидывает ошибку [UpdateFailException]
     */
    override fun create(userId: String, data: OnBoardingInfo) {
        userUserRepository

    }

}