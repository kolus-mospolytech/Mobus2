package com.example.currencyconvertus.data_local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currencyconvertus.data_local.dao.CurrencyDao
import com.example.currencyconvertus.data_local.dao.FavoriteDao
import com.example.currencyconvertus.data_local.dao.HistoryDao
import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import com.example.currencyconvertus.data_local.entity.FavoriteEntity
import com.example.currencyconvertus.data_local.entity.HistoryEntity

@Database(
    entities = [CurrencyEntity::class, FavoriteEntity::class, HistoryEntity::class],
    version = 1
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun historyDao(): HistoryDao
}