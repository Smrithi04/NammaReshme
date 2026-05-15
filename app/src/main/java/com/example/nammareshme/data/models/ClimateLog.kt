package com.example.nammareshme.data.models

data class ClimateLog(
    val id: String = "",
    val batchId: String = "",
    val timestamp: Long = 0,
    val temperature: Double = 0.0,
    val humidity: Double = 0.0,
    val advice: String = "",
    val status: String = "safe"
)
