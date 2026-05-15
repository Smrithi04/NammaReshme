package com.example.nammareshme.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nammareshme.ui.screens.*

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // SPLASH
        composable("splash") {
            SplashScreen(navController)
        }

        // AUTH
        composable("auth") {
            AuthScreen(
                onAuthSuccess = { isNewUser ->
                    if (isNewUser) {
                        navController.navigate("onboarding") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else {
                        navController.navigate("dashboard") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                }
            )
        }

        // ONBOARDING
        composable("onboarding") {
            OnboardingScreen(
                onLanguageSelected = {
                    navController.navigate("dashboard") {
                        popUpTo("onboarding") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // DASHBOARD
        composable("dashboard") {
            DashboardScreen(navController, hiltViewModel())
        }

        // LOGS
        composable("logs") {
            ClimateLogsScreen(navController, hiltViewModel())
        }

        // BATCH
        composable("batch") {
            BatchScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }

        // HISTORY
        composable("history") {
            BatchHistoryScreen(navController, hiltViewModel())
        }

        // PROFILE
        composable("profile") {
            ProfileScreen(navController)
        }

        // CLIMATE ENTRY
        composable("climate_entry") {
            ClimateEntryScreen(navController, hiltViewModel())
        }
    }
}