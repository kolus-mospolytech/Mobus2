package com.example.currencyconvertus.data_local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconvertus.data_local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favoriteEntity: FavoriteEntity)

    @Delete
    suspend fun delete(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    suspend fun getAll(): List<FavoriteEntity>
}