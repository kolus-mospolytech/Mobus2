package com.example.currencyconvertus.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.currencyconvertus.data_local.Converters
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*

@Entity(tableName = "currency")
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = true) @NotNull val id: Int,

    @TypeConverters(Converters::class)
    @ColumnInfo(name = "timestamp") @NotNull var timestamp: Date,
    @ColumnInfo(name = "date") @NotNull var date: Date,
    @ColumnInfo(name = "last_used_at") @Nullable var lastUse: Date? = null,

    @ColumnInfo(name = "base") @NotNull var base: String,
    @ColumnInfo(name = "name") @NotNull var name: String,
    @ColumnInfo(name = "value") @NotNull var value: Double,
)
//{
//    constructor() : this(0, Date(), Date(), Date(), "", "", 0.0)
//}