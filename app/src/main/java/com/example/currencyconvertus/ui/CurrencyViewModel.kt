package com.example.currencyconvertus.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertus.domain.repository.CurrencyRepository
import com.example.currencyconvertus.ui.mapper.CurrencyUIModelMapper
import com.example.currencyconvertus.ui.mapper.HistoryUIModelMapper
import com.example.currencyconvertus.ui.model.CurrenciesUIModel
import com.example.currencyconvertus.ui.model.ExchangeUI
import com.example.currencyconvertus.ui.model.HistoryUIModel
import kotlinx.coroutines.launch
import java.util.*

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    val rates = MutableLiveData<CurrenciesUIModel>()
    val exchange = MutableLiveData<ExchangeUI>()
    val history = MutableLiveData<HistoryUIModel>()

    fun init() {
        viewModelScope.launch {
            repository.getCurrencies().let {
                rates.postValue(CurrencyUIModelMapper.mapDomainModelToUIModel(it))
            }
            repository.getHistory().let {
                history.postValue(HistoryUIModelMapper.mapDomainModelToUIModel(it))
            }
        }
    }

    fun toggleFavorite(name: String, favState: Boolean) {
        viewModelScope.launch {
            val newRates = rates.value
            newRates?.rates?.firstOrNull { it.name == name }?.apply { favorite = !favorite }
            rates.postValue(newRates)
            sortCurrencies()
            repository.toggleFavorite(name, favState)
        }
    }

    private fun sortCurrencies() {
        viewModelScope.launch {
            val sortedCurrencies = rates.value
            sortedCurrencies?.rates?.sortWith(
                compareBy(
                    { !it.favorite },
                    { it.lastUse },
                    { it.name })
            )
            rates.postValue(sortedCurrencies)
        }
    }

    fun addHistoryEntry(
        timestamp: Date,
        currency1: String,
        rate1: Double,
        value1: Double,
        currency2: String,
        rate2: Double,
        value2: Double,
        base: String,
    ) {
        viewModelScope.launch {
            repository.addHistoryEntry(
                timestamp,
                currency1,
                rate1,
                value1,
                currency2,
                rate2,
                value2,
                base,
            )
        }
    }

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */

//    @Suppress("RedundantSuspendModifier")
//    suspend fun getRates(): CurrencyResponse {
//        return repository.getRates()
//    }
//
//    fun get() {
//        viewModelScope.launch {
//            rates.postValue(repository.getRates())
//        }
//    }
}

//class CurrencyViewModelFactory(private val repository: CurrencyRepository) :
//    ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return CurrencyViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}