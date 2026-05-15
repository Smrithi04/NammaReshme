package com.example.nammareshme.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.X
import com.example.nammareshme.viewmodel.MainViewModel

@Composable
fun ClimateEntryScreen(navController: NavController, viewModel: MainViewModel? = null) {

    val activeBatch by (viewModel?.activeBatch?.collectAsState() ?: remember { mutableStateOf(null) })
    val scope = rememberCoroutineScope()

    var temperature by remember { mutableStateOf("25") }
    var humidity by remember { mutableStateOf("75") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F2EE))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 82.dp)
        ) {

            Spacer(modifier = Modifier.height(14.dp))

            // HEADER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF8F8F6)
                )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 16.dp
                    )
                ) {

                    Row {
                        Text(
                            text = "RESHME-",
                            color = Color(0xFF0F4B35),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )

                        Text(
                            text = "NAMMA",
                            color = Color(0xFFD0A81E),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "DIGITAL SERICULTURE ASSISTANT",
                        color = Color(0xFF99A199),
                        fontSize = 10.sp,
                        letterSpacing = 0.7.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            // MAIN CARD
            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .shadow(
                        elevation = 0.dp,
                        shape = RoundedCornerShape(28.dp)
                    )
                    .background(
                        color = Color(0xFFF8F8F6),
                        shape = RoundedCornerShape(28.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF0F4B35),
                        shape = RoundedCornerShape(28.dp)
                    )
            ) {

                Column(
                    modifier = Modifier.padding(
                        horizontal = 26.dp,
                        vertical = 22.dp
                    )
                ) {

                    // TITLE ROW
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {

                        Column {

                            Text(
                                text = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.log_climate),
                                color = Color(0xFF0F4B35),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = activeBatch?.let { "Recording for: ${it.name}" }
                                    ?: "Ensure readings are taken accurately from",
                                color = Color(0xFF5F6F7A),
                                fontSize = 13.sp
                            )

                            if (activeBatch == null) {
                                Text(
                                    text = "your hygrometer at bed level.",
                                    color = Color(0xFF5F6F7A),
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clickable { navController.popBackStack() }
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFFB8BEC4),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Icon(
                                imageVector = Lucide.X,
                                contentDescription = "Close",
                                tint = Color(0xFF8F98A0),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // TEMP UNIT SWITCH
                    Row(
                        modifier = Modifier
                            .width(126.dp)
                            .height(46.dp)
                            .background(
                                color = Color(0xFFEDEDED),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(18.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "°C",
                                color = Color(0xFF0F4B35),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = "°F",
                                color = Color(0xFF7C8288),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(34.dp))

                    // TEMPERATURE
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.temperature),
                        color = Color(0xFF95A193),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = temperature,
                        onValueChange = {
                            if (it.isEmpty() || it.toDoubleOrNull() != null) {
                                temperature = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F4B35)
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFD0D3D6),
                            unfocusedBorderColor = Color(0xFFD0D3D6),
                            focusedContainerColor = Color(0xFFF8F8F6),
                            unfocusedContainerColor = Color(0xFFF8F8F6),
                            cursorColor = Color(0xFF0F4B35)
                        ),
                        trailingIcon = {
                            Text(
                                text = "°C",
                                color = Color(0xFF70856E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // HUMIDITY
                    Text(
                        text = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.humidity),
                        color = Color(0xFF95A193),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = humidity,
                        onValueChange = {
                            if (it.isEmpty() || it.toDoubleOrNull() != null) {
                                humidity = it
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F4B35)
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFD0D3D6),
                            unfocusedBorderColor = Color(0xFFD0D3D6),
                            focusedContainerColor = Color(0xFFF8F8F6),
                            unfocusedContainerColor = Color(0xFFF8F8F6),
                            cursorColor = Color(0xFF0F4B35)
                        ),
                        trailingIcon = {
                            Text(
                                text = "%",
                                color = Color(0xFF70856E),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // BUTTON
                    Button(
                        onClick = {
                            val tempVal = temperature.toDoubleOrNull() ?: 25.0
                            val humVal = humidity.toDoubleOrNull() ?: 75.0
                            val batchId = activeBatch?.id

                            if (batchId != null) {
                                val (advice, status) = calculateHealth(tempVal, humVal)
                                viewModel?.addClimateLog(batchId, tempVal, humVal, advice, status)
                                navController.navigate("logs") {
                                    popUpTo("dashboard")
                                }
                            }
                        },
                        enabled = activeBatch != null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFD0A81E),
                            disabledContainerColor = Color.Gray
                        )
                    ) {

                        Text(
                            text = if (activeBatch == null) androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.initialize_cycle) else "ANALYZE HEALTH",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
        val currentRoute =
            navController.currentBackStackEntryAsState()
                .value?.destination?.route
        BottomNavBar(
            navController = navController,
            currentRoute = currentRoute,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * Calculates health status and provides advice based on temperature and humidity.
 */
private fun calculateHealth(temp: Double, hum: Double): Pair<String, String> {
    val advice = mutableListOf<String>()
    var status = "SAFE"

    if (temp < 24) {
        advice.add("Temperature is low")
        advice.add("Deploy charcoal stoves or heaters immediately")
        status = "WARNING"
    } else if (temp > 28) {
        advice.add("Temperature is high")
        advice.add("Ensure excellent cross-ventilation")
        status = "WARNING"
    }

    if (hum < 70) {
        advice.add("Humidity is low")
        advice.add("Spray fresh water on the floor")
        status = if (status == "SAFE") "WARNING" else "CRITICAL"
    } else if (hum > 85) {
        advice.add("Humidity is high")
        advice.add("Ensure ventilation and avoid wet leaves")
        status = if (status == "SAFE") "WARNING" else "CRITICAL"
    }

    if (advice.isEmpty()) {
        advice.add("Climate conditions are optimal.")
    }

    return advice.joinToString("|") to status
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClimatePreview() {
    val navController = rememberNavController()
    ClimateEntryScreen(navController)
}