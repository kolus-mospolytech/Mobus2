package com.example.currencyconvertus.ui.model

import java.util.*

data class HistoryUIModel(
    val entries: MutableList<HistoryEntryUI>,
    val startDate: Date? = null,
    val endDate: Date? = null
)