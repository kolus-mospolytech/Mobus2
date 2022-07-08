package com.example.currencyconvertus.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertus.domain.repository.CurrencyRepository
import com.example.currencyconvertus.ui.mapper.CurrencyUIModelMapper
import com.example.currencyconvertus.ui.model.CurrenciesUIModel
import com.example.currencyconvertus.ui.model.CurrencyUI
import kotlinx.coroutines.launch
import java.util.*

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    val rates = MutableLiveData<CurrenciesUIModel>()

    fun init() {
        viewModelScope.launch {
            repository.getCurrencies().let {
                rates.postValue(CurrencyUIModelMapper.mapDomainModelToUIModel(it))
            }
//            sortCurrencies()
        }
    }

    fun toggleFavorite(name: String, favState: Boolean) {
        viewModelScope.launch {
            val newRates = rates.value
//            val updatedRate = newRates?.rates?.firstOrNull { it.name == name }
//            if (updatedRate != null) {
//                updatedRate.favorite = !updatedRate.favorite
//            }
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