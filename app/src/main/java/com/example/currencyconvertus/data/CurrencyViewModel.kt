package com.example.currencyconvertus.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CurrencyViewModel(private val repository: CurrencyRepository) : ViewModel() {
    val rates: MutableLiveData<CurrencyResponse> = MediatorLiveData()

    /**
     * Launching a new coroutine to get the data in a non-blocking way
     */

    @Suppress("RedundantSuspendModifier")
    suspend fun getRates(): CurrencyResponse {
        return repository.getRates()
    }

    fun get() {
        viewModelScope.launch {
            rates.postValue(repository.getRates())
        }
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