# Reshme-Namma: Android Jetpack Compose Adaption

This document provides a translation of the React components from the `Reshme-Namma` web application into Kotlin and Jetpack Compose for Android development. 

You can copy and paste these composables into your Android Studio project. Note that you'll need to set up Firebase for Android natively and pass the necessary state holders (ViewModels) to these composables.

## 1. Data Models (`Models.kt`)

```kotlin
import java.util.Date

enum class BatchStatus { ACTIVE, ARCHIVED }
enum class LogStatus { safe, caution, danger }

data class InstarData(
    val id: String,
    val name: String,
    val optimalTempMin: Double,
    val optimalTempMax: Double,
    val optimalHumMin: Double,
    val optimalHumMax: Double,
    val durationDays: Int
)

data class LogEntry(
    val id: String = "",
    val timestamp: Long = Date().time,
    val temp: Double = 0.0,
    val humidity: Double = 0.0,
    val advice: String = "",
    val status: LogStatus = LogStatus.safe,
    val dbBatchId: String? = null
)

data class Batch(
    val id: String = "",
    val name: String = "",
    val breed: String = "",
    val startDate: String = "",
    val status: BatchStatus = BatchStatus.ACTIVE,
    val schedule: String? = null,
    val logs: List<LogEntry> = emptyList()
)
```

## 2. Shared/UI Components (`Components.kt`)

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryGreen = Color(0xFF1A472A)
val AccentGold = Color(0xFFC5A021)
val BackgroundCream = Color(0xFFF7F5F0)
val SurfaceWhite = Color(0xFFFFFFFF)

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = PrimaryGreen,
    icon: @Composable (() -> Unit)? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = RoundedCornerShape(16.dp)
    ) {
        if (icon != null) {
            icon()
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(text.uppercase(), fontWeight = FontWeight.Black, letterSpacing = 1.sp)
    }
}
```

## 3. Auth Screen (`AuthScreen.kt`)

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: (String, String, String) -> Unit
) {
    var isLogin by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reshme-Namma", fontSize = 32.sp, fontWeight = FontWeight.Black, color = PrimaryGreen)
        Spacer(modifier = Modifier.height(32.dp))

        if (!isLogin) {
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            text = if (isLogin) "Sign In" else "Create Account",
            onClick = {
                if (isLogin) onLoginClick(email, password)
                else onRegisterClick(email, password, username)
            }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "Need an account? Sign up" else "Already have an account? Sign in", color = PrimaryGreen)
        }
    }
}
```

## 4. Main Navigation Host (`AppNavigation.kt`)

```kotlin
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(activeBatch: Batch?) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("dashboard") { DashboardScreen(activeBatch, navController) }
            composable("new_batch") { NewBatchScreen(navController) }
            composable("batch_logs") { BatchLogsScreen(activeBatch) }
            composable("history") { HistoryScreen() }
            composable("profile") { ProfileScreen() }
            composable("log_climate") { LogClimateScreen(activeBatch, navController) }
            composable("settings") { SettingsScreen(activeBatch, navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf("dashboard", "batch_logs", "new_batch", "history", "profile")
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = SurfaceWhite) {
        items.forEach { route ->
            NavigationBarItem(
                icon = { /* Map string to appropriate Icon */ },
                label = { Text(route.replace("_", " ").uppercase(), fontSize = 10.sp) },
                selected = currentRoute == route,
                onClick = {
                    navController.navigate(route) {
                        popUpTo("dashboard") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PrimaryGreen,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}
```

## 5. Dashboard Screen (`DashboardScreen.kt`)

```kotlin
@Composable
fun DashboardScreen(activeBatch: Batch?, navController: NavHostController) {
    if (activeBatch == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No Active Batch", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = PrimaryGreen)
            Spacer(modifier = Modifier.height(16.dp))
            PrimaryButton(text = "Start New Batch", onClick = { navController.navigate("new_batch") })
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Active Batch: ${activeBatch.breed}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        // Add instar current stage logic and UI here similar to the React app
        
        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(
            text = "LOG CURRENT CLIMATE",
            color = AccentGold,
            onClick = { navController.navigate("log_climate") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = { /* End batch logic */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEE), contentColor = Color(0xFFD32F2F)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("END BATCH", fontWeight = FontWeight.Bold)
        }
    }
}
```

## 6. Log Climate Screen (`LogClimateScreen.kt`)

```kotlin
@Composable
fun LogClimateScreen(activeBatch: Batch?, navController: NavHostController) {
    var temp by remember { mutableStateOf("25") }
    var humidity by remember { mutableStateOf("70") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Log Climate", fontSize = 24.sp, fontWeight = FontWeight.Black, color = PrimaryGreen)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = temp,
            onValueChange = { temp = it },
            label = { Text("Temperature (°C)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = humidity,
            onValueChange = { humidity = it },
            label = { Text("Humidity (%)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
        
        PrimaryButton(
            text = "SAVE CLIMATE DATA",
            onClick = {
                // Perform save action
                navController.popBackStack()
            }
        )
    }
}
```

## 7. Profile Screen (`ProfileScreen.kt`)

```kotlin
@Composable
fun ProfileScreen() {
    var profileName by remember { mutableStateOf("Farmer Name") }
    
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Your Profile", fontSize = 24.sp, fontWeight = FontWeight.Black, color = PrimaryGreen)
        Spacer(modifier = Modifier.height(24.dp))
        
        OutlinedTextField(
            value = profileName,
            onValueChange = { profileName = it },
            label = { Text("Display Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        // Extrapolate language selection toggle here using Segmented Buttons or Radio Buttons
        Text("Language Preference", fontWeight = FontWeight.Bold)
        
        Spacer(modifier = Modifier.height(32.dp))
        PrimaryButton(text = "SAVE CHANGES", onClick = { /* Update user profile */ })
    }
}
```

## Implementing in Android Studio:
1.  **Dependencies**: Add Navigation Compose (`androidx.navigation:navigation-compose:2.7.x`), Material 3 (`androidx.compose.material3:material3:1.2.x`), and Firebase (`com.google.firebase:firebase-auth`, `firebase-firestore`).
2.  **ViewModels**: Create a ViewModel to manage state (fetching batches from Firestore, updating logic).
3.  **State Hoisting**: Pass state from your ViewModel down to these un-opinionated Composables to render successfully.
