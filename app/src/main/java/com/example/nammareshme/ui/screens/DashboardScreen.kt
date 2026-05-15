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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.icons.lucide.Bug
import com.composables.icons.lucide.CirclePlus
import com.composables.icons.lucide.Clock
import com.composables.icons.lucide.Droplets
import com.composables.icons.lucide.FileText
import com.composables.icons.lucide.LayoutDashboard
import com.composables.icons.lucide.LogOut
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Sparkles
import com.composables.icons.lucide.User
import com.composables.icons.lucide.Wind
import com.composables.icons.lucide.X
import com.example.nammareshme.viewmodel.AuthViewModel
import com.example.nammareshme.viewmodel.MainViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: MainViewModel? = null,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    // Collect active batch state from ViewModel
    val activeBatch by (viewModel?.activeBatch?.collectAsState() ?: remember { mutableStateOf(null) })
    val latestLog by (viewModel?.latestLog?.collectAsState() ?: remember { mutableStateOf(null) })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF3F3F3))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            if (activeBatch == null) {
                InitialDashboard(navController, authViewModel)
            } else {
                ActiveDashboard(navController, viewModel, authViewModel, activeBatch!!, latestLog)
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
fun InitialDashboard(navController: NavController, authViewModel: AuthViewModel) {
    Spacer(modifier = Modifier.height(16.dp))

    // 🔝 HEADER CARD
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text(
                    "RESHME-",
                    color = Color(0xFF1B5E3B),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(
                    "NAMMA",
                    color = Color(0xFFD4A017),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Text(
                "DIGITAL SERICULTURE ASSISTANT",
                fontSize = 11.sp,
                color = Color(0xFF7A8F83)
            )
        }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // Greeting Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Namaskara, ${authViewModel.currentUser?.displayName?.ifBlank { "User" } ?: "User"}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E3B)
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, Color(0xFFCAD5CE), RoundedCornerShape(12.dp))
                .clickable {
                    authViewModel.signOut()
                    navController.navigate("auth") {
                        popUpTo(0) { inclusive = true }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Lucide.LogOut,
                contentDescription = "Logout",
                tint = Color(0xFF1B5E3B),
                modifier = Modifier.size(20.dp)
            )
        }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // 🌿 HERO CARD
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .background(
                brush = Brush.horizontalGradient(listOf(Color(0xFF1B5E3B), Color(0xFF2F6F3E))),
                shape = RoundedCornerShape(28.dp)
            )
            .padding(20.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Lucide.Sparkles,
                    contentDescription = "Icon",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "SERICULTURE\nINTELLIGENCE",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 30.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Discover insights on rearing optimal yields. Polyvoltine breeds are hardy and suited for tropical climates year-round, while Bivoltine races yield premium quality silk but require exceptionally strict climate control.",
                color = Color.White.copy(alpha = 0.85f),
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate("batch") },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4A017))
            ) {
                Text(
                    text = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.initialize_cycle),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(20.dp))

    // INFO CARDS
    InfoCard(
        icon = Lucide.Wind,
        title = "SUMMER REARING",
        desc = "During hot seasons, ensure excellent cross-ventilation. Keep rearing trays covered with damp newspaper to maintain micro-humidity and prevent leaf wilting."
    )

    InfoCard(
        icon = Lucide.Droplets,
        title = "MONSOON CARE",
        desc = "High humidity breeds fungal diseases. Use bed disinfectants like Vijetha after every molt. Ensure mulberry leaves are completely dry before feeding."
    )

    InfoCard(
        icon = Lucide.Bug,
        title = "BIVOLTINE NEEDS",
        desc = "High-yielding CSR breeds are highly sensitive. Late instars need temperatures exactly around 24-25°C. Any fluctuation drastically reduces cocoon quality.",
        iconBgColor = Color(0xFFE8F5E9),
        iconTintColor = Color(0xFF2E7D32)
    )
}

@Composable
fun ActiveDashboard(
    navController: NavController,
    viewModel: MainViewModel?,
    authViewModel: AuthViewModel,
    batch: com.example.nammareshme.data.models.Batch,
    latestLog: com.example.nammareshme.data.models.ClimateLog?
) {
    Spacer(modifier = Modifier.height(16.dp))

    // HEADER
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row {
                Text("RESHME-", color = Color(0xFF1B5E3B), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("NAMMA", color = Color(0xFFD4A017), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Text("DIGITAL SERICULTURE ASSISTANT", fontSize = 11.sp, color = Color(0xFF7A8F83))
        }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // Greeting Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Namaskara, ${authViewModel.currentUser?.displayName?.ifBlank { "User" } ?: "User"}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E3B)
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .border(1.dp, Color(0xFFCAD5CE), RoundedCornerShape(12.dp))
                .clickable {
                    authViewModel.signOut()
                    navController.navigate("auth") { popUpTo(0) { inclusive = true } }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = Lucide.LogOut, contentDescription = "Logout", tint = Color(0xFF1B5E3B), modifier = Modifier.size(20.dp))
        }
    }

    Spacer(modifier = Modifier.height(22.dp))

    // BATCH OVERVIEW CARD
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFFD4A017), RoundedCornerShape(12.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text("BATCH #${batch.name.uppercase()}", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("SELECTED BREED", fontSize = 12.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
                    Text(batch.breed, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B5E3B))
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text("DAY COUNT", fontSize = 12.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
                    Text("Day 1", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B5E3B))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel?.archiveBatch(batch.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFF1F1)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFD1D1))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Lucide.X, contentDescription = null, tint = Color.Red, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("END BATCH", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // CURRENT STAGE CARD
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF1B5E3B))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("CURRENT STAGE", fontSize = 14.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
                Icon(imageVector = Lucide.Bug, contentDescription = null, tint = Color(0xFF1B5E3B), modifier = Modifier.size(24.dp).background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(4.dp))
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(verticalAlignment = Alignment.Bottom) {
                Text("1", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B5E3B))
                Text("Instar", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E3B), modifier = Modifier.padding(bottom = 8.dp, start = 4.dp))
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text("Day 1 of 3 for INSTAR_I", fontSize = 14.sp, color = Color(0xFF5F6F7A))
            Text("TOTAL DAY 1 TO END: DAY 3", fontSize = 11.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = { 0.33f },
                modifier = Modifier.fillMaxWidth().height(10.dp),
                color = Color(0xFF1B5E3B),
                trackColor = Color(0xFFE0E0E0),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // IDEAL CONDITIONS CARD
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text("IDEAL CONDITIONS", fontSize = 14.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).background(Color(0xFF1B5E3B), CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Current: INSTAR_I", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E3B))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ConditionBox(label = "TEMPERATURE", value = "27° - 28°C", modifier = Modifier.weight(1f))
                ConditionBox(label = "HUMIDITY", value = "85% - 90%", modifier = Modifier.weight(1f))
            }
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // LAST LOGGED CARDS
    Row(modifier = Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        DataCard(label = "LAST TEMP", value = "${latestLog?.temperature ?: "--"}°C", icon = Lucide.Wind, modifier = Modifier.weight(1f))
        DataCard(label = "LAST HUM", value = "${latestLog?.humidity ?: "--"}%", icon = Lucide.Droplets, modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(24.dp))

    // HARVEST ALERT
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFD4A017))
    ) {
        Box(modifier = Modifier.padding(24.dp)) {
            Column {
                Text("HARVEST ALERT", fontSize = 14.sp, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text("24", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                    Text("Days", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(bottom = 8.dp, start = 8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Until transfer to Spinning Trays (Chandrike). Prepare mounts.", fontSize = 14.sp, color = Color.White.copy(alpha = 0.9f))
            }
            Box(modifier = Modifier.size(8.dp).background(Color.White, CircleShape).align(Alignment.TopEnd))
        }
    }

    Spacer(modifier = Modifier.height(24.dp))

    // LOG CLIMATE BUTTON
    Button(
        onClick = { navController.navigate("climate_entry") },
        modifier = Modifier.fillMaxWidth().height(80.dp).padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD4A017)),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Lucide.CirclePlus, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text("LOG CURRENT CLIMATE", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }
    }
    
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun ConditionBox(label: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, fontSize = 10.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E3B))
        }
    }
}

@Composable
fun DataCard(label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {
            Column {
                Text(label, fontSize = 11.sp, color = Color(0xFF9AA3AA), fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF1B5E3B))
            }
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFE0E0E0), modifier = Modifier.size(48.dp).align(Alignment.CenterEnd))
        }
    }
}

@Composable
fun InfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    desc: String,
    iconBgColor: Color = Color(0xFFFFE5D0),
    iconTintColor: Color = Color(0xFFD4A017)
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F7F7))
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            iconBgColor,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTintColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    title,
                    color = Color(0xFF1B5E3B),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                desc,
                fontSize = 13.sp,
                color = Color(0xFF5F6F7A)
            )
        }
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(78.dp)
            .background(
                Color(0xFFF8F8F6),
                RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)
            )
            .padding(horizontal = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        NavItem(
            label = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.dashboard),
            icon = Lucide.LayoutDashboard,
            selected = currentRoute == "dashboard"
        ) {
            navController.navigate("dashboard")
        }

        NavItem(
            label = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.logs),
            icon = Lucide.FileText,
            selected = currentRoute == "logs"
        ) {
            navController.navigate("logs")
        }

        NavItem(
            label = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.batch),
            icon = Lucide.CirclePlus,
            selected = currentRoute == "batch"
        ) {
            navController.navigate("batch")
        }

        NavItem(
            label = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.history),
            icon = Lucide.Clock,
            selected = currentRoute == "history"
        ) {
            navController.navigate("history")
        }

        NavItem(
            label = androidx.compose.ui.res.stringResource(com.example.nammareshme.R.string.profile),
            icon = Lucide.User,
            selected = currentRoute == "profile"
        ) {
            navController.navigate("profile")
        }
    }
}

@Composable
fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    val color =
        if (selected)
            Color(0xFF2D3142)
        else
            Color(0xFF9AA3AA)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onClick()
        }
    ) {

        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = label,
            fontSize = 10.sp,
            color = color,
            fontWeight =
                if (selected)
                    FontWeight.Bold
                else
                    FontWeight.Medium
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    DashboardScreen(navController)
}