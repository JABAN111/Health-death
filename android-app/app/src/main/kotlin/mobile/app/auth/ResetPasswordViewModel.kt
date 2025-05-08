package mobile.app.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import mobile.app.utils.ValidationResult
import mobile.app.utils.Validators

class ResetPasswordViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        val error = validateEmail(email)
        _uiState.getAndUpdate {it.copy(email = email, emailError = error)}
    }

    fun validateForm(): List<String> {
        return listOf(
            validateEmail(_uiState.value.email),
        ).filterNotNull()
    }

    private fun validateEmail(email: String): String? {
        return if (Validators.validateEmail(email) is ValidationResult.Invalid) {
            "Некорректный email"
        } else null
    }
}

data class ResetPasswordUiState(
    val email: String = "",
    val emailError: String? = null,
)