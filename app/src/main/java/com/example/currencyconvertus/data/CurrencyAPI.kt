package com.example.currencyconvertus.data

import retrofit2.http.GET

interface CurrencyAPI {

    @GET("/latest.js")
    suspend fun getLatestRates(): CurrencyResponse
}