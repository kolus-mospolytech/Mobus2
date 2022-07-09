package com.example.currencyconvertus.domain.mapper

import com.example.currencyconvertus.data_local.entity.HistoryEntity
import com.example.currencyconvertus.domain.model.HistoryEntry
import com.example.currencyconvertus.domain.model.HistoryLocal

object HistoryDtoMapper {
    fun mapDatabaseToDomainModel(
        history: List<HistoryEntity>,
    ): HistoryLocal {
        val parsedHistory: MutableList<HistoryEntry> = mutableListOf()

        history.forEach {
            parsedHistory.add(
                HistoryEntry(
                    timestamp = it.timestamp,
                    currency1 = it.currency1,
                    rate1 = it.rate1,
                    value1 = it.value1,
                    currency2 = it.currency2,
                    rate2 = it.rate2,
                    value2 = it.value2,
                    base = it.base,
                )
            )
        }

        return HistoryLocal(
            entries = parsedHistory,
        )
    }
}