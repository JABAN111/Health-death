package mobile.app

enum class Screen(val route: String) {
    Welcome("welcome"),
    Register("register"),
    Login("login"),
    PasswordRecover("password_recover"),
    Onboarding("onboarding"),
    Diary("diary")
}