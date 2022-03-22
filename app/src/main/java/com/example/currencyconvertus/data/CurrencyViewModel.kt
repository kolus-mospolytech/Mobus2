package com.example.currencyconvertus.data

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    val rates: MutableLiveData<CurrencyResponse> by lazy {
        MediatorLiveData()
    }

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */

    @Suppress("RedundantSuspendModifier")
    suspend fun getRates(): CurrencyResponse {
        return repository.getRates()
    }
}

class CurrencyViewModelFactory(private val repository: CurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}