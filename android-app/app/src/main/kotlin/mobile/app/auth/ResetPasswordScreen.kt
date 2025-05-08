package mobile.app.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mobile.app.components.OutlinedTextFieldWithIcon
import mobile.app.ui.theme.mainGreen

@Composable
fun ResetPasswordScreen(
    onLoginClicked: () -> Unit,
    onPasswordRecovered: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = viewModel<LoginViewModel>()
    val uiState = viewModel.uiState.collectAsState().value
    val mContext = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Забыли пароль?",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 128.dp)
        )
        Text(
            text = "Введите email для восстановления",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        OutlinedTextFieldWithIcon(
            value = uiState.email,
            onValueChange = viewModel::onEmailChanged,
            label = "Email",
            leadingIcon = Icons.Default.Email,
            isError = uiState.emailError != null
        )

        Button(
            onClick = {
                val valid = viewModel.validateForm()
                if (valid.size == 0) {
                    onPasswordRecovered
                } else {
                    Toast.makeText(
                        mContext,
                        valid.getOrNull(0) ?: "Что-то пошло не так",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .height(220.dp)
                .padding(0.dp, 150.dp, 0.dp, 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = mainGreen)
        ) {
            Text(
                text = "Восстановить пароль",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }

        TextButton(
            onClick = onLoginClicked,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Вернуться ко входу",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                textDecoration = TextDecoration.Underline
            )
        }
    }
}