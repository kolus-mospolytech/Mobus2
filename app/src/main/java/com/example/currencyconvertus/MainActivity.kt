package com.example.currencyconvertus

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconvertus.data_remote.CurrencyResponse
import com.example.currencyconvertus.databinding.ActivityMainBinding
import com.example.currencyconvertus.ui.AnalyticsFragment
import com.example.currencyconvertus.ui.CurrencyViewModel
import com.example.currencyconvertus.ui.FilterFragment
import com.example.currencyconvertus.ui.HistoryFagment
import com.example.currencyconvertus.ui.exchange.CurrencyListFragment
import com.example.currencyconvertus.ui.exchange.ExchangeFragment
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rates: CurrencyResponse
    private val fragmentList: MutableList<Fragment> = mutableListOf()
    private var position = 0
    private lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext

        currencyViewModel = RepositoryDependency.viewModel
        currencyViewModel.init()

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

//        GlobalScope.launch(Dispatchers.IO) {
//            val currencies = RepositoryDependency.repository.getCurrencies()
//            Log.d("MY_TAG", "$currencies")
//            Log.d("MY_TAG is success", "${currencies.rates}")
//        }

//        val ratesObserver = Observer<CurrencyResponse> { newRates ->
//            rates = newRates
//            Log.d("amoamoamo", rates.toString())
//        }

//        currencyViewModel.rates.observe(this, ratesObserver)
//        currencyViewModel.get()
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

    companion object {
        lateinit var appContext: Context
    }
}

