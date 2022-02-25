package com.example.currencyconvertus.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    companion object {
        fun provideCurrencyDAO(): CurrencyDAO {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.nokk3r.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CurrencyDAO::class.java)
        }
    }

}