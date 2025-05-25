package service

import model.OnBoardingInfo

interface OnBoardingService {
    /**
     * Создает запись в хранилище, в случае провала выкидывает ошибку [UpdateFailException]
     */
    fun create(userId: String, data: OnBoardingInfo)
}