package com.example.currencyconvertus.data_source

import com.example.currencyconvertus.data_remote.CurrencyApi
import com.example.currencyconvertus.data_remote.CurrencyResponse

// Вызывает функцию getLatestRates() чтобы получить данные с веб-сервера
class RemoteDataSource(private val currencyApi: CurrencyApi) {

    suspend fun getLatestRates(): CurrencyResponse {
        return currencyApi.getLatestRates()
    }
}

//class RemoteDataSource {
//    companion object {
//        fun provideCurrencyDAO(): CurrencyAPI {
//            val interceptor = HttpLoggingInterceptor()
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//            val retrofit = Retrofit.Builder()
//                .client(client)
//                .baseUrl("https://www.cbr-xml-daily.ru/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//
//            return retrofit.create(CurrencyAPI::class.java)
//        }
//    }
//
//}
