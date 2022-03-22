package com.example.currencyconvertus

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.currencyconvertus.data.CurrencyResponse
import com.example.currencyconvertus.data.CurrencyViewModel
import com.example.currencyconvertus.data.CurrencyViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var rates: CurrencyResponse
    private val currencyViewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory((application as CurrencyConvertus).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratesObserver = Observer<CurrencyResponse> { newRates ->
            rates = newRates
            Log.d("amoamoamo", rates.toString())
        }

        currencyViewModel.rates.observe(this, ratesObserver)
        currencyViewModel.get()
    }
}

