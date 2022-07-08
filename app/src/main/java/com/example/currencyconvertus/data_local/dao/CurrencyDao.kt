package com.example.currencyconvertus.data_local.dao

import androidx.room.*
import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import java.util.*

@Dao
interface CurrencyDao {
    @Insert
    suspend fun insert(currencyEntity: CurrencyEntity)

    @Insert
    suspend fun insertMany(currencyList: List<CurrencyEntity>)

    @Update
    suspend fun update(currencyEntity: CurrencyEntity)

    @Update
    suspend fun updateMany(currencyList: List<CurrencyEntity>)

    @Delete
    suspend fun delete(currencyEntity: CurrencyEntity)

    @Delete
    suspend fun deleteMany(currencyList: List<CurrencyEntity>)

    @Query("DELETE FROM currency WHERE date = (:date)")
    suspend fun deleteByDate(date: Date)

    @Query("DELETE FROM currency WHERE date < date('now', '-1 month') ")
    suspend fun deleteOld()

    @Query("SELECT * FROM currency WHERE date = (:date) ORDER BY last_used_at DESC, name")
    suspend fun getByDate(date: Date): List<CurrencyEntity>
}