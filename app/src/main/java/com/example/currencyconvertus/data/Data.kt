package com.example.currencyconvertus.data

import retrofit2.http.GET

interface Data {

    @GET("/api/latest?access_key=85e42e0b0709ecd8e93444f304a7dc5b")
    suspend fun GetBebrus(): OtvetAmogusa
}