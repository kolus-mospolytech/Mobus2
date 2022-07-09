package com.example.currencyconvertus.ui.model

data class ExchangeUI(
    val amount1: Double = 1.0,
    val currency1: CurrencyUI,
    val amount2: Double = 0.0,
    val currency2: CurrencyUI
)