package mobile.app.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import java.sql.Date

class OnboardingInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingInfoUiState())
    val uiState: StateFlow<OnboardingInfoUiState> = _uiState.asStateFlow()

    fun onSexChanged(isFemale: Boolean) {
        _uiState.getAndUpdate { it.copy(female = isFemale) }
    }

    fun onHeightChanged(height: Int?) {
        _uiState.getAndUpdate { it.copy(height = height) }
    }

    fun onWeightChanged(weight: Int?) {
        _uiState.getAndUpdate { it.copy(weight = weight) }
    }

    fun onBirthdayChanged(day: Int, month: Int, year: Int) {
        _uiState.getAndUpdate { it.copy(birthday = Date(year - 1900, month, day + 1)) }
    }

    fun onBirthdayChangedRaw(timestamp: Long) {
        _uiState.getAndUpdate { it.copy(birthday = Date(timestamp)) }
    }

    fun validateFirstPart(): String? {
        if (_uiState.value.height == null)
            return "Вы не указали рост"
        if (_uiState.value.weight == null)
            return "Вы не указали вес"
        return null
    }

    fun onTargetChanged(target: DietTarget) {
        _uiState.getAndUpdate { it.copy(target = target) }
    }

    fun onTargetWeightChanged(targetWeight: Int?) {
        _uiState.getAndUpdate { it.copy(targetWeight = targetWeight) }
    }

    fun onTrainingsPerWeekChanged(trainingsPerWeek: Int?) {
        _uiState.getAndUpdate { it.copy(trainingsPerWeek = trainingsPerWeek) }
    }

    fun onTargetDeadlineChanged(day: Int, month: Int, year: Int) {
        _uiState.getAndUpdate { it.copy(targetDeadline = Date(year - 1900, month, day + 1)) }
    }

    fun onTargetDeadlineChangedRaw(time: Long) {
        _uiState.getAndUpdate { it.copy(targetDeadline = Date(time)) }
    }

    fun onDietaryPatternChanged(pattern: String) {
        _uiState.getAndUpdate { it.copy(dietaryPattern = pattern) }
    }

    fun onDietaryScheduleChanged(dietSchedule: DietSchedule) {
        _uiState.getAndUpdate { it.copy(dietarySchedule = dietSchedule) }
    }

    fun validateSecondPart(): String? {
        if (_uiState.value.target == DietTarget.SaveWeight && _uiState.value.targetWeight == null)
            return "Вы не указали цель веса"
        if (_uiState.value.trainingsPerWeek == null)
            return "Вы не указали количество тренировок в неделю"
        if (_uiState.value.targetDeadline == Date(0L))
            return "Вы не указали дату окончания цели"
        if (_uiState.value.dietaryPattern == "")
            return "Вы не указали тип питания"
        return null
    }
}

data class OnboardingInfoUiState(
    val female: Boolean = true,
    val height: Int? = 0,
    val weight: Int? = 0,
    val birthday: Date = Date(0L),
    val target: DietTarget = DietTarget.SaveWeight,
    val targetWeight: Int? = 0,
    val trainingsPerWeek: Int? = null,
    val targetDeadline: Date = Date(0L),
    val dietaryPattern: String = "",
    val dietarySchedule: DietSchedule = DietSchedule(0, 0,0),
)

public enum class DietTarget(val title: String) {
    SaveWeight("Поддержать вес"),
    LoseWeight("Похудеть"),
    GetWeight("Набрать вес")
}

public enum class DietPattern(val title: String) {
    Classic("Классический"),
    Vegetarian("Вегетарианство"),
    Vegan("Веганство"),
    Pescetarian("Пескетарианство"),
}

public data class DietSchedule(val breakfast: Int, val lunch: Int, val dinner: Int)
public data class NamedDietSchedule(val name: String, val schedule: DietSchedule)


val larkSchedule = NamedDietSchedule("Жаворонок", DietSchedule(7 * 60, 13 * 60, 19 * 60))
val normalSchedule = NamedDietSchedule("Обычный", DietSchedule(8 * 60, 12 * 60, 18 * 60))
val owlSchedule = NamedDietSchedule("Сова", DietSchedule(9 * 60, 14 * 60, 20 * 60))