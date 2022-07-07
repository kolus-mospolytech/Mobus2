package com.example.currencyconvertus.data_remote

// Формат данных с веб-сервера
data class CurrencyResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)

