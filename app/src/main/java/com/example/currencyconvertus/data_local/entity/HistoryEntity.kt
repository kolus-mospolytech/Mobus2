package com.example.currencyconvertus.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.currencyconvertus.data_local.Converters
import org.jetbrains.annotations.NotNull
import java.util.*

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) @NotNull val id: Int,
    @TypeConverters(Converters::class)
    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP") @NotNull val timestamp: Date,

    @ColumnInfo(name = "currency_1") @NotNull val currency1: String,
    @ColumnInfo(name = "rate_1") @NotNull val rate1: Double,
    @ColumnInfo(name = "value_1") @NotNull val value1: Double,

    @ColumnInfo(name = "currency_2") @NotNull val currency2: String,
    @ColumnInfo(name = "rate_2") @NotNull val rate2: Double,
    @ColumnInfo(name = "value_2") @NotNull val value2: Double,

    @ColumnInfo(name = "base") @NotNull val base: String,
)
//{
//    constructor() : this(0, Date(), "", 0.0, 0.0, "", 0.0, 0.0, "")
//}