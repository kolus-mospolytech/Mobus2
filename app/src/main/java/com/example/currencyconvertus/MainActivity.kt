package com.example.currencyconvertus

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.currencyconvertus.data.CurrencyResponse
import com.example.currencyconvertus.data.CurrencyViewModel
import com.example.currencyconvertus.data.CurrencyViewModelFactory
import com.example.currencyconvertus.databinding.ActivityMainBinding
import com.example.currencyconvertus.misc.CurrencyHolder
import com.example.currencyconvertus.ui.*
import com.example.currencyconvertus.ui.exchange.CurrencyListFragment
import com.example.currencyconvertus.ui.exchange.ExchangeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rates: CurrencyResponse
    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private var position = 0
    private val currencyViewModel: CurrencyViewModel by viewModels {
        CurrencyViewModelFactory((application as CurrencyConvertus).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentList.add(CurrencyListFragment())
        fragmentList.add(ExchangeFragment())
        fragmentList.add(HistoryFagment())
        fragmentList.add(FilterFragment())
        fragmentList.add(AnalyticsFragment())

        val transactionInitialization = supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragmentList[0])
            .addToBackStack("initialization fragment")
        transactionInitialization.commit()

//        CurrencyListFragment().adapter.itemList = CurrencyHolder.createCurrencyList()
//        CurrencyListFragment().adapter.notifyDataSetChanged()

        binding.bottomNavigation.selectedItemId = R.id.screen_exchange

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.screen_exchange -> {
                    navigateTo(0)
                }
                R.id.screen_history -> {
                    navigateTo(2)
                }
                R.id.screen_analytics -> {
                    navigateTo(4)
                }
            }
            true
        }

        val ratesObserver = Observer<CurrencyResponse> { newRates ->
            rates = newRates
            Log.d("amoamoamo", rates.toString())
        }

        currencyViewModel.rates.observe(this, ratesObserver)
        currencyViewModel.get()
    }

    private fun navigateTo(to: Int) {
        val transactionInitialization = supportFragmentManager
            .beginTransaction()
            .remove(fragmentList[position])
            .replace(R.id.fragment_container, fragmentList[to])
            .addToBackStack("swap fragment")
        transactionInitialization.commit()
        position = to
    }
}

