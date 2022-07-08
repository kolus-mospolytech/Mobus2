package com.example.currencyconvertus.data_local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconvertus.data_local.entity.HistoryEntity
import java.util.*

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    @Delete
    suspend fun delete(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history WHERE timestamp BETWEEN (:startDate) AND (:endDate)")
    suspend fun getBetweenDates(startDate: Date, endDate: Date): List<HistoryEntity>
}