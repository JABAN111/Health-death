package mobile.app.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mobile.app.R
import mobile.app.ui.theme.mainLightGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingInfoScreenFirst(
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<OnboardingInfoViewModel>()
    val uiState = viewModel.uiState.collectAsState().value
    val mContext = LocalContext.current

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Основная информация",
            style = typography.headlineLarge,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .padding(16.dp, 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
        ) {
            item {
                Column(
                    modifier = Modifier
                        .background(
                            if (uiState.female) mainLightGreen else Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(8.dp)
                        .clickable { viewModel.onSexChanged(true) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.female),
                        contentDescription = "Женщина",
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Женщина",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
            item {
                Column(
                    modifier = Modifier
                        .background(
                            if (uiState.female) Color.White else mainLightGreen,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(8.dp)
                        .clickable { viewModel.onSexChanged(false) }
                ) {
                    Image(
                        painter = painterResource(R.drawable.male),
                        contentDescription = "Мужчина",
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Мужчина",
                        style = typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }
        }



        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp)
                .background(Color.White, shape = RoundedCornerShape(15.dp))
        ) {
            Text(
                text = "Рост, см",
                style = typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
                )
            TextField(
                value = uiState.height.toString(),
                onValueChange = { viewModel.onHeightChanged(it.toInt()) },
                        modifier = Modifier
                        .padding(16.dp, 8.dp)
            )
            Slider(
                value = uiState.height.toFloat(),
                onValueChange = { viewModel.onHeightChanged(it.toInt()) },
                steps = 200,
                valueRange = 50f..250f,
                modifier = Modifier
                    .padding(16.dp, 8.dp),
//                thumb = {
//                    Image(
//                        painter = painterResource(R.drawable.thumb),
//                        contentDescription = "Рост",
//                        modifier = Modifier
//                            .align(Alignment.CenterHorizontally)
//                            .background(Color.Red)
//                    )
//                }
            )
        }
    }
}