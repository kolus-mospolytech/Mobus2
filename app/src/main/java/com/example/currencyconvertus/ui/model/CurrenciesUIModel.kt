package com.example.currencyconvertus.ui.model

import com.example.currencyconvertus.domain.model.Currency


data class CurrenciesUIModel(
    val base: String,
    val rates: MutableList<CurrencyUI>
)