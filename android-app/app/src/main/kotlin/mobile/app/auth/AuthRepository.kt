import android.content.Context
import androidx.core.content.edit

class AuthRepository(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    var isAuthenticated: Boolean
        get() = prefs.getBoolean("is_authenticated", false)
        set(value) = prefs.edit() { putBoolean("is_authenticated", value) }

    fun clearAuth() = prefs.edit() { clear() }
}