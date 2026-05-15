package com.example.nammareshme.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.CircleCheck
import com.composables.icons.lucide.CircleX
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.TriangleAlert
import com.example.nammareshme.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.collections.emptyList

@Composable
fun ClimateLogsScreen(navController: NavController, viewModel: MainViewModel? = null) {

    // Collect active batch state from ViewModel
    val activeBatch by (viewModel?.activeBatch?.collectAsState() ?: remember { mutableStateOf(null) })
    
    val logsFlow = remember(activeBatch?.id) {
        activeBatch?.id?.let { viewModel?.getLogsForBatch(it) } 
    }
    val logs by (logsFlow?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf(emptyList()) })

    val dateFormat = SimpleDateFormat("M/d/yyyy 'at' h:mm:ss a", LocalLocale.current.platformLocale)

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
                    .weight(1f)
                    .padding(horizontal = 12.dp)
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
                        horizontal = 18.dp,
                        vertical = 22.dp
                    )
                ) {

                    // TITLE ROW
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {

                        Text(
                            text = if (activeBatch != null) "${activeBatch?.name}\nLOGS" else "CURRENT\nBATCH LOGS",
                            color = Color(0xFF0F4B35),
                            fontSize = 22.sp,
                            lineHeight = 28.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    if (logs.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = if (activeBatch == null) "No active batch found." else "No climate logs recorded yet.",
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        val reversedLogs = remember(logs) { logs.reversed() }
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(18.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(reversedLogs) { log ->
                                val statusColor = when (log.status) {
                                    "CRITICAL" -> Color(0xFFE53935)
                                    "WARNING" -> Color(0xFFD0A81E)
                                    else -> Color(0xFF4CAF50)
                                }
                                val statusIcon = when (log.status) {
                                    "CRITICAL" -> Lucide.CircleX
                                    "WARNING" -> Lucide.TriangleAlert
                                    else -> Lucide.CircleCheck
                                }

                                LogCard(
                                    iconBg = statusColor,
                                    icon = statusIcon,
                                    tempHumidity = "${log.temperature}°C / ${log.humidity}%",
                                    time = dateFormat.format(Date(log.timestamp)),
                                    advice = log.advice
                                )
                            }
                        }
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

@Composable
fun LogCard(
    iconBg: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tempHumidity: String,
    time: String,
    advice: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F2EE)
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(
                verticalAlignment = Alignment.Top
            ) {

                // STATUS ICON
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .background(
                            color = iconBg,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = "Log Status",
                        tint = Color.White,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = tempHumidity,
                        color = Color(0xFF122C34),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = time,
                        color = Color(0xFF8B8B8B),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // ADVICE BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF8F8F6),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {

                Column {

                    Text(
                        text = "Actions & Advice:",
                        color = Color(0xFF0F4B35),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = advice.replace("|", "\n"),
                        color = Color(0xFF243B53),
                        fontSize = 13.sp,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClimateLogPreview() {
    val navController = rememberNavController()
    ClimateLogsScreen(navController)
}