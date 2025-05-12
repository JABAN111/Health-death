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

    fun onHeightChanged(height: Int) {
        _uiState.getAndUpdate { it.copy(height = height) }
    }

    fun onWeightChanged(weight: Int) {
        _uiState.getAndUpdate { it.copy(weight = weight) }
    }

    fun onBirthdayChanged(day: Int, month: Int, year: Int) {
        _uiState.getAndUpdate { it.copy(birthday = Date(year - 1900, month, day + 1)) }
    }

    fun onTargetChanged(target: DietTarget) {
        _uiState.getAndUpdate { it.copy(target = target) }
    }

    fun onTargetWeightChanged(targetWeight: Int) {
        _uiState.getAndUpdate { it.copy(targetWeight = targetWeight) }
    }

    fun onTrainingsPerWeekChanged(trainingsPerWeek: Int) {
        _uiState.getAndUpdate { it.copy(trainingsPerWeek = trainingsPerWeek) }
    }

    fun onTargetDeadlineChanged(day: Int, month: Int, year: Int) {
        _uiState.getAndUpdate { it.copy(targetDeadline = Date(year - 1900, month, day + 1)) }
    }

    fun onDietaryPatternChanged(pattern: String) {
        _uiState.getAndUpdate { it.copy(dietaryPattern = pattern) }
    }

    fun onDietaryScheduleChanged(breakfast: Int, lunch: Int, dinner: Int) {
        _uiState.getAndUpdate { it.copy(dietarySchedule = DietSchedule(breakfast, lunch, dinner)) }
    }
}

data class OnboardingInfoUiState(
    val female: Boolean = true,
    val height: Int = 0,
    val weight: Int = 0,
    val birthday: Date = Date(0L),
    val target: DietTarget = DietTarget.SaveWeight,
    val targetWeight: Int = 0,
    val trainingsPerWeek: Int = 0,
    val targetDeadline: Date = Date(0L),
    val dietaryPattern: String = "",
    val dietarySchedule: DietSchedule = DietSchedule(0, 0,0),
)

public enum class DietTarget(title: String) {
    SaveWeight("Поддержать вес"),
    LoseWeight("Похудеть"),
    GetWeight("Набрать вес")
}

public enum class DietPattern(title: String) {
    Classic("Классический"),
    Vegetarian("Вегетарианство"),
    Vegan("Веганство"),
    Pescetarian("Пескетарианство"),
}

public data class DietSchedule(val breakfast: Int, val lunch: Int, val dinner: Int)