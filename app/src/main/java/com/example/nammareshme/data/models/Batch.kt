package com.example.nammareshme.data.models

data class Batch(
    val id: String = "",
    val name: String = "",
    val breed: String = "",
    val farmerName: String = "",
    val region: String = "",
    val hatchDate: String = "",
    val status: String = "ACTIVE"
) {
    val isArchived: Boolean
        get() = status == "ARCHIVED"
}
