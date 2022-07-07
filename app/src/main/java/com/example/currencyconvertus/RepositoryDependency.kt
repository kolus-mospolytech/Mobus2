package com.example.currencyconvertus

import androidx.room.Room
import com.example.currencyconvertus.data_local.CurrencyDatabase
import com.example.currencyconvertus.data_remote.CurrencyApi
import com.example.currencyconvertus.data_source.LocalDataSource
import com.example.currencyconvertus.data_source.RemoteDataSource
import com.example.currencyconvertus.domain.repository.CurrencyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryDependency {
    // реализация retrofit и связь с репозиторием
    private val interceptor = HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(CurrencyApi::class.java)

    //Подсос базы данных
    private val instance = Room.databaseBuilder(
        CurrencyConvertus().applicationContext,
        CurrencyDatabase::class.java,
        "exchange-db"
    ).build()

//    @Volatile
//    private var INSTANCE: CurrencyDatabase? = null
//
//    fun getDatabase(
//        context: Context,
//        scope: CoroutineScope
//    ): CurrencyDatabase {
//        // if the INSTANCE is not null, then return it,
//        // if it is, then create the database
//        return INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                context.applicationContext,
//                CurrencyDatabase::class.java,
//                "exchange-db"
//            )
//                .build()
//            INSTANCE = instance
//            instance
//        }
//    }

    private val localDataSource = LocalDataSource(instance)
    private val remoteDataSource = RemoteDataSource(service)

    val repository = CurrencyRepository(localDataSource, remoteDataSource)
}