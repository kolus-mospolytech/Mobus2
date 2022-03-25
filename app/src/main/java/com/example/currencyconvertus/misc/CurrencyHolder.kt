package com.example.currencyconvertus.misc

object CurrencyHolder {
    private val currency = arrayOf(
        "BOBUX",
        "AMO",
        "GUS",
        "OK",
        "I",
        "PULL",
        "UP",
        "BEBRA",
        "HAROSH",
        "PLOH",
    )

    private val rate = arrayOf(
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
        "100.07 VBUX",
    )

    private val favorite = arrayOf(
        true,
        true,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
        false,
    )

    fun createCurrencyList(): List<CurrencyListItem> {
        val currencyList = mutableListOf<CurrencyListItem>()
        for (i in 0..9) {
            val listItem = CurrencyListItem(
                currency[i],
                rate[i],
                favorite[i]
            )
            currencyList.add(listItem)
        }
        return currencyList
    }
}

data class CurrencyListItem(
    val currency: String,
    val rate: String,
    val favorite: Boolean
)