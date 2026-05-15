package com.example.nammareshme.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nammareshme.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun BatchHistoryScreen(navController: NavController, viewModel: MainViewModel? = null) {

    // Collect batch history state from ViewModel
    val batchHistory by (viewModel?.batchHistory?.collectAsState() ?: remember { mutableStateOf(emptyList()) })

    val archivedBatches = remember(batchHistory) {
        batchHistory.filter { it.isArchived }.reversed()
    }

    val dateFormat = SimpleDateFormat("M/d/yyyy", LocalLocale.current.platformLocale)
    val logTimeFormat = SimpleDateFormat("M/d/yyyy\nh:mm:ss a", LocalLocale.current.platformLocale)

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

            Spacer(modifier = Modifier.height(24.dp))

            // MAIN HISTORY CONTAINER
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
                        horizontal = 22.dp,
                        vertical = 26.dp
                    )
                ) {

                    Text(
                        text = "BATCH HISTORY",
                        color = Color(0xFF0F4B35),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    if (archivedBatches.isEmpty()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No archived batches found.",
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(22.dp),
                            modifier = Modifier.fillMaxSize()
                        ) {
                             items(archivedBatches) { item ->
                                 val logs by (viewModel?.getLogsForBatch(item.id)?.collectAsState(initial = emptyList()) ?: remember { mutableStateOf(emptyList()) })
                                 val formattedLogs = logs.map { log ->
                                     Triple(
                                         logTimeFormat.format(Date(log.timestamp)),
                                         "${log.temperature}°C / ${log.humidity}%",
                                         log.status
                                     )
                                 }
                                 HistoryCard(
                                     batchId = item.name,
                                     breed = item.breed,
                                     region = item.region,
                                     farmer = item.farmerName,
                                     started = item.hatchDate,
                                     ended = dateFormat.format(Date()), // Ideally we'd have an archivedDate in DB
                                     logs = formattedLogs
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
fun HistoryCard(
    batchId: String,
    breed: String,
    region: String,
    farmer: String,
    started: String,
    ended: String,
    logs: List<Triple<String, String, String>>
) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .border(width = 1.dp, color = Color(0xFF737373), shape = RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF3F2EE)
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = "BATCH: $batchId",
                color = Color(0xFF0F4B35),
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Breed: $breed |",
                color = Color(0xFF4B5563),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Region: $region | Farmer: $farmer",
                color = Color(0xFF4B5563),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "STARTED $started • ENDED\n$ended",
                color = Color(0xFFD0A81E),
                fontSize = 14.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ARCHIVED BADGE
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFDADDE1),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {

                Text(
                    text = "ARCHIVED",
                    color = Color(0xFF374151),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // LOGS BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color(0xFFF8F8F6),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE0E0E0),
                        shape = RoundedCornerShape(18.dp)
                    )
                    .padding(16.dp)
            ) {

                Column {

                    Text(
                        text = "LOGS (${logs.size})",
                        color = Color(0xFF7E927C),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    logs.forEachIndexed { index, log ->

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column {

                                Text(
                                    text = log.first.substringBefore("\n"),
                                    color = Color(0xFF333333),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = log.first.substringAfter("\n", ""),
                                    color = Color(0xFF8A8A8A),
                                    fontSize = 12.sp
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.End
                            ) {

                                Text(
                                    text = log.second,
                                    color = Color(0xFF0F4B35),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier
                                        .background(
                                            color = Color(0xFFCFF5D8),
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .padding(
                                            horizontal = 10.dp,
                                            vertical = 4.dp
                                        )
                                ) {

                                    Text(
                                        text = "◉ ${log.third}",
                                        color = Color(0xFF0B7A33),
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        if (index != logs.lastIndex) {

                            Spacer(modifier = Modifier.height(16.dp))

                            HorizontalDivider(
                                thickness = 1.dp,
                                color = Color(0xFFE8E8E8)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BatchHistoryPreview() {
    val navController = rememberNavController()
    BatchHistoryScreen(navController)
}