package com.example.currencyconvertus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.currencyconvertus.data.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val interceptus = HttpLoggingInterceptor()
        interceptus.level = HttpLoggingInterceptor.Level.BODY
        val clienter = OkHttpClient.Builder().addInterceptor(interceptus).build()

        val retromogers =  Retrofit.Builder()
            .client(clienter)
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val servicelio = retromogers.create(Data::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val cringe = servicelio.GetBebrus()
            Log.d("2323", "$cringe")
        }
    }

}