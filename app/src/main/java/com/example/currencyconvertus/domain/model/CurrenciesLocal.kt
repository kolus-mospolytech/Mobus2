package com.example.currencyconvertus.domain.model


import java.util.*

// Формат данных в локальной БД
data class CurrenciesLocal(
    val timestamp: Date,
    val base: String,
    val date: Date,
    val rates: List<Currency>
)
