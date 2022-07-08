package com.example.currencyconvertus.ui.model

import java.util.*

data class CurrencyUI(
    val name: String,
    val value: String,
    var favorite: Boolean = false,
    var lastUse: Date? = null
)
