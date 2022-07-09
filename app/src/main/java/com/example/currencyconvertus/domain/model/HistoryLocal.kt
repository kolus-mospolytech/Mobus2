package com.example.currencyconvertus.domain.model

import java.util.*

data class HistoryLocal(
    val entries: MutableList<HistoryEntry>,
    val startDate: Date? = null,
    val endDate: Date? = null
)