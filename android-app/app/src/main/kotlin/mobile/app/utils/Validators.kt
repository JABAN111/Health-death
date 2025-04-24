package mobile.app.utils

object Validators {
    fun validateEmail(email: String): ValidationResult {
        val emailRegex = Regex(
            "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        )
        return if (email.matches(emailRegex)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid("Некорректный email адрес")
        }
    }

    fun validateLogin(login: String): ValidationResult {
        val loginRegex = Regex("^[a-zA-Z0-9а-яА-Я]+$")
        return if (login.matches(loginRegex)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid("Только буквы и цифры без пробелов и спецсимволов")
        }
    }

    fun validatePassword(password: String): ValidationResult {
        val passwordRegex = Regex("^[a-zA-Z0-9а-яА-Я.&?$%*@#]+$")
        return if (password.matches(passwordRegex)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid("Недопустимые символы в пароле")
        }
    }

    fun validateName(name: String): ValidationResult {
        val nameRegex = Regex("^[a-zA-Zа-яА-Я]+$")
        return if (name.matches(nameRegex)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid("Только буквы без пробелов и спецсимволов")
        }
    }
}

sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val message: String) : ValidationResult()
}