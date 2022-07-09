package com.example.currencyconvertus.ui.model

import java.util.*

data class HistoryEntryUI(
    val timestamp: Date,
    val currency1: String,
    val rate1: String,
    val value1: String,
    val currency2: String,
    val rate2: String,
    val value2: String,
    val base: String,
)