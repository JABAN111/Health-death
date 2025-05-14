package mobile.app

enum class Screen(val route: String) {
    Welcome("welcome"),
    Register("register"),
    Login("login"),
    PasswordRecover("password_recover"),
    Onboarding("onboarding"),
    OnboardingInfoFirst("onboarding_info_1"),
    OnboardingInfoSecond("onboarding_info_2"),
    Diary("diary")
}