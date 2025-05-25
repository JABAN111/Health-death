package service

import model.OnBoardingInfo

/**
 * Сервис, который будет добавлять, обновлять в бд информацию о пользовательских данных
 */
class OnBoardingServiceImpl : OnBoardingService {
    /**
     * Создает запись в хранилище, в случае провала выкидывает ошибку [UpdateFailException]
     */
    override fun create(userId: String, data: OnBoardingInfo) {
        TODO("Not yet implemented")
    }

}