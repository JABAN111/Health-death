package mobile.app

import AuthRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val authRepo = AuthRepository(application.applicationContext)

    val isAuthenticated = authRepo.isAuthenticated

    fun markAuthenticated() {
        authRepo.isAuthenticated = true
    }

    fun logout() {
        authRepo.clearAuth()
    }
}