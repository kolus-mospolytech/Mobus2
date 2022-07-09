package com.example.currencyconvertus.domain.model

import java.util.*

data class HistoryEntry(
    val timestamp: Date,
    val currency1: String,
    val rate1: Double,
    val value1: Double,
    val currency2: String,
    val rate2: Double,
    val value2: Double,
    val base: String,
)