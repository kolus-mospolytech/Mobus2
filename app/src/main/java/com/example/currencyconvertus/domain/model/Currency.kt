package com.example.currencyconvertus.domain.model

import java.util.*


data class Currency(
    val name: String,
    val value: Double,
    var favorite: Boolean = false,
    var lastUse: Date? = null
)
