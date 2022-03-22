package com.example.currencyconvertus

import android.app.Application
import com.example.currencyconvertus.data.CurrencyRepository
import com.example.currencyconvertus.data.RemoteDataSource

class CurrencyConvertus : Application() {

    private val currencyDAO by lazy { RemoteDataSource.provideCurrencyDAO() }
    val repository by lazy { CurrencyRepository(currencyDAO) }
}