package com.example.currencyconvertus.data

import androidx.annotation.WorkerThread

class CurrencyRepository(private val currencyDAO: CurrencyDAO) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getRates(): CurrencyResponse {
        return currencyDAO.getLatestRates()
    }
}