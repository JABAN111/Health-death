package mobile.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import mobile.app.ui.theme.mainGreen


@Composable
fun WelcomeScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val typography = MaterialTheme.typography

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        val (box, next) = createRefs()

        val gradient = Brush.linearGradient(
            colors = listOf(
                Color.White.copy(alpha = 0.5f),
                Color(0xFF0AB492),
                Color.White.copy(alpha = 0.5f)
            ),
            start = Offset.Zero,
            end = Offset(1000f, 0f) // Horizontal gradient
        )

        Column (
            modifier = Modifier.constrainAs(box) {
                top.linkTo(parent.top, margin = 16.dp)
                bottom.linkTo(next.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.padding(16.dp)) {
                Text(
                    text = "Health Death",
                    textAlign = TextAlign.Center,
                    style = typography.displayLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 96.sp,
                        lineHeight = 96.sp,
                        brush = gradient
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Text(
                    text = "Добро пожаловать!",
                    style = typography.headlineLarge,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp, 108.dp, 16.dp, 16.dp)
                )


            Text(
                text = "Fitness, Recovery, Energy, Strength and Health",
                style = typography.headlineSmall.copy(
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
            )
        }

        Button(
            onClick = onContinueClicked,
            colors = ButtonDefaults.buttonColors(mainGreen),
            modifier = Modifier
                .constrainAs(next) {
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(16.dp, 0.dp)
        ) {
            Text("Начать")
        }
    }
}