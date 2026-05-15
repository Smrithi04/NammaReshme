package com.example.nammareshme.navigation

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")
    object Logs : Screen("logs")
    object Batch : Screen("batch")
    object History : Screen("history")
    object Profile : Screen("profile")
    object ClimateEntry : Screen("climate_entry")
}