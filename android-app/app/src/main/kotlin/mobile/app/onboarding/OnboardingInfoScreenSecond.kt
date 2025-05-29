package mobile.app.onboarding

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import mobile.app.R
import mobile.app.ui.theme.mainGreen
import mobile.app.ui.theme.mainLightGreen
import java.text.DateFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingInfoScreenSecond(
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
                text = "Цели и предпочтения",
                style = typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
            )
        }

        item {
            Column {
                Text(
                    text = "Цель",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )

                DietTarget.entries.map {
                    Button(
                        onClick = {
                            viewModel.onTargetChanged(it)
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent, Color.Black),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .height(64.dp)
                            .fillMaxWidth()
                            .background(
                                if (uiState.target == it) mainLightGreen else Color.White,
                                shape = RoundedCornerShape(15.dp)
                            ),
                    ) {
                        Text(
                            text = it.title,
                            style = typography.bodyMedium,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .background(Color.Transparent)
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Желаемый вес",
                style = typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
            )
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
                    value = uiState.targetWeight?.toString() ?: "",
                    onValueChange = { viewModel.onTargetWeightChanged(it.toIntOrNull()) },
                    modifier = Modifier
                        .padding(8.dp, 8.dp)
                        .background(Color.Transparent),
                    textStyle = typography.titleMedium,
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color.Transparent),
                    shape = RoundedCornerShape(15.dp)
                )
                Slider(
                    value = uiState.targetWeight?.toFloat() ?: 0f,
                    onValueChange = { viewModel.onTargetWeightChanged(it.toInt()) },
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
            Text(
                text = "Количество тренировок в неделю",
                style = typography.titleMedium,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
            )
        }

        item {
            Row(
                horizontalArrangement = spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
            ) {
                listOf(0, 1, 2, 3, 4, 5).map {
                    Button(
                        colors = ButtonDefaults.buttonColors(mainLightGreen),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { viewModel.onTrainingsPerWeekChanged(it) },
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (uiState.trainingsPerWeek == it) mainGreen else mainLightGreen,
                                shape = RoundedCornerShape(10.dp)
                            ),
                    )
                    { Text(it.toString(), color = Color.Black) }
                }
            }
            Row(
                horizontalArrangement = spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp)
                    .background(Color.White, shape = RoundedCornerShape(15.dp))
            ) {
                listOf(6, 7).map {
                    Button(
                        colors = ButtonDefaults.buttonColors(mainLightGreen),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { viewModel.onTrainingsPerWeekChanged(it) },
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                if (uiState.trainingsPerWeek == it) mainGreen else mainLightGreen,
                                shape = RoundedCornerShape(10.dp)
                            ),
                    )
                    { Text(it.toString(), color = Color.Black) }
                }

                val rememberTrains = remember { mutableStateOf<Int?>(null) }

                Button(
                    colors = ButtonDefaults.buttonColors(mainLightGreen),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {viewModel.onTrainingsPerWeekChanged(rememberTrains.value)},
                    modifier = Modifier
                        .weight(4.5f)
                        .background(
                            if (uiState.trainingsPerWeek != null && uiState.trainingsPerWeek >= 8) mainGreen else mainLightGreen,
                            shape = RoundedCornerShape(10.dp)
                        ),
                ) {
                    Text("Другое:", color = Color.Black)
                    BasicTextField(
                        value = rememberTrains.value?.toString() ?: "",
                        onValueChange = { rememberTrains.value = it.toIntOrNull() ?: 8 },
                        modifier = Modifier
                            .background(Color.Transparent),
                        textStyle = typography.titleMedium,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    text = "Дедлайн цели",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )
                Text(
                    text = DateFormat.getDateInstance().format(uiState.targetDeadline),
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 16.dp, 0.dp)
                )
                Slider(
                    value = (uiState.targetDeadline.time / (3600L * 1000L)).toFloat(),
                    onValueChange = { viewModel.onTargetDeadlineChangedRaw(it.toLong() * 3600 * 1000) },
                    steps = 40_000,
                    valueRange = (System.currentTimeMillis() / (3600L * 1000L)).toFloat() .. (System.currentTimeMillis() / (3600L * 1000L) + 50000).toFloat(),
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
            Column {
                Text(
                    text = "Тип питания",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )
                DietPattern.entries.map {
                    Button(
                        onClick = {
                            viewModel.onDietaryPatternChanged(it.title)
                        },
                        colors = ButtonDefaults.buttonColors(Color.Transparent, Color.Black),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .height(64.dp)
                            .fillMaxWidth()
                            .background(
                                if (uiState.dietaryPattern == it.title) mainLightGreen else Color.White,
                                shape = RoundedCornerShape(15.dp)
                            ),
                    ) {
                        Text(
                            text = it.title,
                            style = typography.bodyMedium,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp, 0.dp)
                                .background(Color.Transparent)
                        )
                    }
                }
            }
        }

        item {
            Column {
                Text(
                    text = "План питания",
                    style = typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp, 8.dp)
                )
                listOf(larkSchedule, normalSchedule, owlSchedule).map {
                    Column {
                        Text(
                            text = it.name,
                            style = typography.titleSmall,
                            modifier = Modifier
                                .padding(16.dp, 8.dp)
                        )
                        Button(
                            onClick = {
                                viewModel.onDietaryScheduleChanged(it.schedule)
                            },
                            colors = ButtonDefaults.buttonColors(Color.Transparent, Color.Black),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .padding(16.dp, 8.dp)
                                .height(84.dp)
                                .fillMaxWidth()
                                .background(
                                    if (uiState.dietarySchedule == it.schedule) mainLightGreen else Color.White,
                                    shape = RoundedCornerShape(15.dp)
                                ),
                        ) {
                            Column {
                                Text(
                                    text = "Завтрак ${it.schedule.breakfast / 60}:${it.schedule.breakfast % 60}",
                                    style = typography.bodyMedium,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 0.dp)
                                        .background(Color.Transparent)
                                )
                                Text(
                                    text = "Обед ${it.schedule.lunch / 60}:${it.schedule.lunch % 60}",
                                    style = typography.bodyMedium,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 0.dp)
                                        .background(Color.Transparent)
                                )
                                Text(
                                    text = "Ужин ${it.schedule.dinner / 60}:${it.schedule.dinner % 60}",
                                    style = typography.bodyMedium,
                                    textAlign = TextAlign.Left,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp, 0.dp)
                                        .background(Color.Transparent)
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Button(
                {
                    val err = viewModel.validateSecondPart()
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