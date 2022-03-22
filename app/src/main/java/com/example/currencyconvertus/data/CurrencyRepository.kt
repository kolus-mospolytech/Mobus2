package com.example.currencyconvertus.data

import androidx.annotation.WorkerThread

class CurrencyRepository(private val currencyDAO: CurrencyAPI) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getRates(): CurrencyResponse {
        return currencyDAO.getLatestRates()
    }
}