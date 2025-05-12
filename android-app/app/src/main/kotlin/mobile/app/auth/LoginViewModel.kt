package mobile.app.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import mobile.app.utils.ValidationResult
import mobile.app.utils.Validators

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChanged(email: String) {
        val error = validateEmail(email)
        _uiState.getAndUpdate {it.copy(email = email, emailError = error)}
    }

    fun onPasswordChanged(password: String) {
        val error = validatePassword(password)
        _uiState.getAndUpdate {it.copy(password = password, passwordError = error)}
    }

    fun validateForm(): List<String> {
        return listOf(
            validateEmail(_uiState.value.email),
            validatePassword(_uiState.value.password),
        ).filterNotNull()
    }

    private fun validateEmail(email: String): String? {
        return if (Validators.validateEmail(email) is ValidationResult.Invalid) {
            "Некорректный email"
        } else null
    }

    private fun validatePassword(password: String, minLength: Int = 8): String? {
        return if (Validators.validatePassword(password) is ValidationResult.Invalid) {
            "Недопустимые символы в пароле"
        } else if (password.length < minLength) {
            "Пароль должен содержать не менее $minLength символов"
        } else null
    }
}

data class LoginUiState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val showPassword: Boolean = false
)