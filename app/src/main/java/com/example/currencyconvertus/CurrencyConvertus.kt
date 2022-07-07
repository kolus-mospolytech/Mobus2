package com.example.currencyconvertus

import android.app.Application
import android.content.Context
import com.example.currencyconvertus.data_source.RemoteDataSource
import com.example.currencyconvertus.domain.repository.CurrencyRepository
import com.google.android.material.color.DynamicColors

class CurrencyConvertus : Application() {
//    private val currencyDAO by lazy { RemoteDataSource.provideCurrencyDAO() }
//    val repository by lazy { CurrencyRepository(currencyDAO) }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}