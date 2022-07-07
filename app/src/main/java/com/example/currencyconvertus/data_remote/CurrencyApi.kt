package com.example.currencyconvertus.data_remote

import retrofit2.http.GET

// Полученгие данных с веб-сервиса
interface CurrencyApi {

    @GET("/latest.js")
    suspend fun getLatestRates(): CurrencyResponse
}