package com.example.currencyconvertus.ui.mapper

import com.example.currencyconvertus.domain.model.HistoryLocal
import com.example.currencyconvertus.ui.model.HistoryEntryUI
import com.example.currencyconvertus.ui.model.HistoryUIModel

object HistoryUIModelMapper {
    fun mapDomainModelToUIModel(history: HistoryLocal): HistoryUIModel {
        val entriesUI = mutableListOf<HistoryEntryUI>()

        history.entries.forEach {
            entriesUI.add(
                HistoryEntryUI(
                    timestamp = it.timestamp,
                    currency1 = it.currency1,
                    rate1 = it.rate1.toString(),
                    value1 = it.value1.toString(),
                    currency2 = it.currency2,
                    rate2 = it.rate2.toString(),
                    value2 = it.value2.toString(),
                    base = it.base,
                )
            )
        }


        return HistoryUIModel(
            entries = entriesUI,
        )
    }
}