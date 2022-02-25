package com.example.currencyconvertus

import android.app.Application
import com.example.currencyconvertus.data.CurrencyRepository
import com.example.currencyconvertus.data.RemoteDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CurrencyConvertus : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val currencyDAO by lazy { RemoteDataSource.provideCurrencyDAO() }
    val repository by lazy { CurrencyRepository(currencyDAO) }
}