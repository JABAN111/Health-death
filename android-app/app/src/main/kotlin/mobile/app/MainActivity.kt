package mobile.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mobile.app.auth.LoginScreen
import mobile.app.auth.RegisterScreen
import mobile.app.auth.ResetPasswordScreen
import mobile.app.ui.theme.HealthdeathTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthdeathTheme {
                val navController = rememberNavController()
                val viewModel = viewModel<MainViewModel>()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        startDestination = if (viewModel.isAuthenticated) Screen.Onboarding.route else Screen.Welcome.route,
                        navController = navController,
                    ) {
                        composable(Screen.Welcome.route) {
                            WelcomeScreen(onContinueClicked = {
                                navController.navigateSingleTopTo(
                                    Screen.Register.route
                                )
                            })
                        }

                        composable(Screen.Register.route) {
                            RegisterScreen(
                                onRegister = {},
                                onExistingAccountClicked = { navController.navigateSingleTopTo(Screen.Login.route) })
                        }

                        composable(Screen.Login.route) {
                            LoginScreen(
                                onLogin = {},
                                onPasswordResetClicked = { navController.navigateSingleTopTo(Screen.PasswordRecover.route) },
                                onCreateAccountClicked = { navController.navigateSingleTopTo(Screen.Register.route) })
                        }

                        composable(Screen.PasswordRecover.route) {
                            ResetPasswordScreen(
                                onLoginClicked = { navController.navigateSingleTopTo(Screen.Login.route) },
                                onPasswordRecovered = { navController.navigateSingleTopTo(Screen.Login.route) }
                            );
                        }
                    }
                }
            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }