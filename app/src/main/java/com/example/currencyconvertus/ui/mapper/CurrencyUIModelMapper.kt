package com.example.currencyconvertus.ui.mapper

import com.example.currencyconvertus.domain.model.CurrenciesLocal
import com.example.currencyconvertus.ui.model.CurrenciesUIModel
import com.example.currencyconvertus.ui.model.CurrencyUI

object CurrencyUIModelMapper {
    fun mapDomainModelToUIModel(currencies: CurrenciesLocal): CurrenciesUIModel {
        val ratesUI = mutableListOf<CurrencyUI>()

        currencies.rates.forEach {
            ratesUI.add(
                CurrencyUI(
                    name = it.name,
                    value = (1 / it.value).toString(),
                    favorite = it.favorite,
                    lastUse = it.lastUse
                )
            )
        }

        ratesUI.sortWith(
            compareBy(
                { !it.favorite },
                { it.lastUse },
                { it.name })
        )

        return CurrenciesUIModel(
            base = currencies.base,
            rates = ratesUI
        )
    }
}