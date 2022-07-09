package com.example.currencyconvertus.domain.mapper

import android.annotation.SuppressLint
import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import com.example.currencyconvertus.data_local.entity.FavoriteEntity
import com.example.currencyconvertus.data_local.entity.HistoryEntity
import com.example.currencyconvertus.data_remote.CurrencyResponse
import com.example.currencyconvertus.domain.model.CurrenciesLocal
import com.example.currencyconvertus.domain.model.Currency
import com.example.currencyconvertus.domain.model.HistoryEntry
import com.example.currencyconvertus.domain.model.HistoryLocal
import com.example.currencyconvertus.ui.model.HistoryUIModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

object CurrencyDtoMapper {

    fun mapResponseToDatabase(
        response: CurrencyResponse,
        storedRates: MutableList<CurrencyEntity>? = null
    ): List<CurrencyEntity> {
        val parsedRates: MutableList<CurrencyEntity> = mutableListOf()

        val parsedTimestamp = Date(System.currentTimeMillis())
        val parsedDate = LocalDateTime.now().toString()
        val parsedBase = response.base

        if (storedRates != null) {
            for ((name, value) in response.rates) {
                val storedCurrency = storedRates.firstOrNull { it.name == name }

                if (storedCurrency != null) {
                    storedCurrency.timestamp = parsedTimestamp
                    storedCurrency.date =
                        SimpleDateFormat("yyyy-MM-dd").parse(parsedDate) as Date
                    storedCurrency.base = parsedBase
                    storedCurrency.name = name
                    storedCurrency.value = value
                } else {
                    storedRates.add(
                        CurrencyEntity(
                            id = 0,
                            timestamp = parsedTimestamp,
                            date = SimpleDateFormat("yyyy-MM-dd").parse(parsedDate) as Date,
                            base = parsedBase,
                            name = name,
                            value = value
                        )
                    )
                }
            }

            return storedRates
        } else {
            for ((name, value) in response.rates) {
                parsedRates.add(
                    CurrencyEntity(
                        id = 0,
                        timestamp = parsedTimestamp,
                        date = SimpleDateFormat("yyyy-MM-dd").parse(parsedDate) as Date,
                        base = parsedBase,
                        name = name,
                        value = value
                    )
                )
            }

            parsedRates.add(
                CurrencyEntity(
                    id = 0,
                    timestamp = parsedTimestamp,
                    date = SimpleDateFormat("yyyy-MM-dd").parse(parsedDate) as Date,
                    base = parsedBase,
                    name = parsedBase,
                    value = 1.0
                )
            )

            return parsedRates
        }
    }

    fun mapDatabaseToDomainModel(
        rates: List<CurrencyEntity>,
        favorites: List<FavoriteEntity>? = null
    ): CurrenciesLocal {
        val parsedRates: MutableList<Currency> = mutableListOf()
        val parsedFavorites: MutableList<String> = mutableListOf()

        favorites?.forEach {
            parsedFavorites.add(it.currency)
        }

        rates.forEach {
            parsedRates.add(
                Currency(
                    name = it.name,
                    value = it.value,
                    favorite = it.name in parsedFavorites,
                    lastUse = it.lastUse
                )
            )
        }

        return CurrenciesLocal(
            timestamp = rates.first().timestamp,
            base = rates.first().base,
            date = rates.first().date,
            rates = parsedRates
        )
    }

    // Конвертация данных из формата CurrencyResponse.kt в формат CurrenciesLocal.kt
    @SuppressLint("SimpleDateFormat")
    fun mapResponseToDomainModel(response: CurrencyResponse): CurrenciesLocal {
        val parsedRates: MutableList<Currency> = mutableListOf()

        for ((name, value) in response.rates) {
            parsedRates.add(
                Currency(
                    name = name,
                    value = value
                )
            )
        }

        return CurrenciesLocal(
            timestamp = Date(response.timestamp),
            base = response.base,
            date = SimpleDateFormat("yyyy-MM-dd").parse(response.date) as Date,
            rates = parsedRates
        )
    }
}