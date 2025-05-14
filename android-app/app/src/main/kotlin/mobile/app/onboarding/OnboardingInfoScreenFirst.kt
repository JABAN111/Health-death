package mobile.app.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import mobile.app.ui.theme.mainGreen
import mobile.app.ui.theme.mainLightGreen
import java.text.DateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingInfoScreenFirst(
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel<OnboardingInfoViewModel>()
    val uiState = viewModel.uiState.collectAsState().value
    val mContext = LocalContext.current

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = "Основная информация",
                style = typography.headlineLarge,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            if (uiState.female) mainLightGreen else Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(8.dp)
                        .clickable { viewModel.onSexChanged(true) }
                        .weight(1f)
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

                Column(
                    modifier = Modifier
                        .background(
                            if (uiState.female) Color.White else mainLightGreen,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(8.dp)
                        .clickable { viewModel.onSexChanged(false) }
                        .weight(1f)
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



        item {
            Column(
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
                    value = uiState.height?.toString() ?: "",
                    onValueChange = { viewModel.onHeightChanged(it.toIntOrNull()) },
                    modifier = Modifier
                        .padding(8.dp, 8.dp)
                        .background(Color.Transparent),
                    textStyle = typography.titleMedium,
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    shape = RoundedCornerShape(15.dp)
                )
                Slider(
                    value = uiState.height?.toFloat() ?: 0f,
                    onValueChange = { viewModel.onHeightChanged(it.toInt()) },
                    steps = 200,
                    valueRange = 50f..250f,
                    modifier = Modifier
                        .padding(4.dp, 0.dp)
                        .offset(0.dp, 20.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = mainGreen,
                        activeTickColor = mainGreen,
                        inactiveTrackColor = mainLightGreen,
                        inactiveTickColor = mainLightGreen
                    ),
                    thumb = {
                        Image(
                            painter = painterResource(R.drawable.thumb),
                            contentDescription = "Рост",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(32.dp)
                                .height(32.dp)
                        )
                    },
                )
            }
        }



        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
            ) {
                Text(
                    text = "Вес, кг",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )
                TextField(
                    value = uiState.weight?.toString() ?: "",
                    onValueChange = { viewModel.onWeightChanged(it.toIntOrNull()) },
                    modifier = Modifier
                        .padding(8.dp, 8.dp)
                        .background(Color.Transparent),
                    textStyle = typography.titleMedium,
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    shape = RoundedCornerShape(15.dp)
                )
                Slider(
                    value = uiState.weight?.toFloat() ?: 0f,
                    onValueChange = { viewModel.onWeightChanged(it.toInt()) },
                    steps = 200,
                    valueRange = 0f..250f,
                    modifier = Modifier
                        .padding(4.dp, 0.dp)
                        .offset(0.dp, 20.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = mainGreen,
                        activeTickColor = mainGreen,
                        inactiveTrackColor = mainLightGreen,
                        inactiveTickColor = mainLightGreen
                    ),
                    thumb = {
                        Image(
                            painter = painterResource(R.drawable.thumb),
                            contentDescription = "Рост",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(32.dp)
                                .height(32.dp)
                        )
                    },
                )
            }
        }



        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
            ) {
                Text(
                    text = "День рождения",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )
                Text(
                    text = DateFormat.getDateInstance().format(uiState.birthday),
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 0.dp)
                )
                Slider(
                    value = (uiState.birthday.time / (3600L * 1000L)).toFloat(),
                    onValueChange = { viewModel.onBirthdayChangedRaw(it.toLong() * 3600 * 1000) },
                    steps = 40_000,
                    valueRange = -350640f..(System.currentTimeMillis() / (3600L * 1000L)).toFloat(),
                    modifier = Modifier
                        .padding(4.dp, 0.dp)
                        .offset(0.dp, 20.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = mainGreen,
                        activeTickColor = mainGreen,
                        inactiveTrackColor = mainLightGreen,
                        inactiveTickColor = mainLightGreen
                    ),
                    thumb = {
                        Image(
                            painter = painterResource(R.drawable.thumb),
                            contentDescription = "Рост",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .width(32.dp)
                                .height(32.dp)
                        )
                    },
                )
            }
        }

        item {
            Button(
                {
                    val err = viewModel.validateFirstPart()
                    if (err == null)
                        onNext()
                    else
                        Toast.makeText(mContext, err, Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 32.dp, 16.dp, 0.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = mainGreen)
            ) {
                Text("Далее")
            }
        }
    }
}