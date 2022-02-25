package com.example.currencyconvertus

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconvertus.data.CurrencyResponse
import com.example.currencyconvertus.data.CurrencyViewModel
import com.example.currencyconvertus.data.CurrencyViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var rates: CurrencyResponse
    private val currencyViewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory((application as CurrencyConvertus).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.IO) {
            rates = currencyViewModel.getRates()
            Log.d("amogus2", rates.toString())
        }
    }
}

