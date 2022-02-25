package com.example.currencyconvertus.data

import retrofit2.http.GET

interface CurrencyDAO {

    @GET("/")
    suspend fun getLatestRates(): CurrencyResponse
}