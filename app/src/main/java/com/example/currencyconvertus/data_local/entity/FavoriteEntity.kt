package com.example.currencyconvertus.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true) @NotNull val id: Int,
    @ColumnInfo(name = "currency") @NotNull val currency: String,
)
//{
//    constructor() : this(0, "")
//}