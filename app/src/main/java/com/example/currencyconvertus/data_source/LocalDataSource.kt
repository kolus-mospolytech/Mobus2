package com.example.currencyconvertus.data_source

import com.example.currencyconvertus.data_local.CurrencyDatabase
import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import com.example.currencyconvertus.data_local.entity.FavoriteEntity
import com.example.currencyconvertus.data_local.entity.HistoryEntity
import java.util.*

// Локальное хранилище данных (БД)
class LocalDataSource(private val currencyDatabase: CurrencyDatabase) {

    suspend fun getLocalRates(): List<CurrencyEntity> {
        return currencyDatabase.currencyDao().getByDate(Date())
    }

    suspend fun addRates(currencyList: List<CurrencyEntity>) {
        return currencyDatabase.currencyDao().insertMany(currencyList)
    }

    suspend fun updateRates(currencyList: List<CurrencyEntity>) {
        return currencyDatabase.currencyDao().updateMany(currencyList)
    }

    suspend fun updateAccessTime(currencyEntity: CurrencyEntity) {
        currencyEntity.lastUse = java.sql.Date(System.currentTimeMillis())

        return currencyDatabase.currencyDao().update(currencyEntity)
    }

    suspend fun purgeOldRates() {
        return currencyDatabase.currencyDao().deleteOld()
    }

    suspend fun getFavorites(): List<FavoriteEntity>? {
        return currencyDatabase.favoriteDao().getAll()
    }

    suspend fun addToFavorite(favoriteEntity: FavoriteEntity) {
        return currencyDatabase.favoriteDao().insert(favoriteEntity)
    }

    suspend fun deleteFromFavorite(favoriteEntity: FavoriteEntity) {
        return currencyDatabase.favoriteDao().delete(favoriteEntity)
    }

    suspend fun addToHistory(historyEntity: HistoryEntity) {
        return currencyDatabase.historyDao().insert(historyEntity)
    }

    suspend fun getHistoryEntries(startDate: Date, endDate: Date): List<HistoryEntity> {
        return currencyDatabase.historyDao().getBetweenDates(startDate, endDate)
    }
}