package com.example.currencyconvertus.data

data class OtvetAmogusa(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

