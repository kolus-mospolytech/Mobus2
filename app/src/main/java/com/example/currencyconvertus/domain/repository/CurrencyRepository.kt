package com.example.currencyconvertus.domain.repository

import android.util.Log
import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import com.example.currencyconvertus.data_local.entity.FavoriteEntity
import com.example.currencyconvertus.data_local.entity.HistoryEntity
import com.example.currencyconvertus.data_remote.CurrencyResponse
import com.example.currencyconvertus.data_source.LocalDataSource
import com.example.currencyconvertus.data_source.RemoteDataSource
import com.example.currencyconvertus.domain.mapper.CurrencyDtoMapper
import com.example.currencyconvertus.domain.mapper.HistoryDtoMapper
import com.example.currencyconvertus.domain.model.CurrenciesLocal
import com.example.currencyconvertus.domain.model.HistoryLocal
import java.util.*


class CurrencyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getCurrencies(): CurrenciesLocal {
//        try {
        val storedRates: List<CurrencyEntity> = localDataSource.getLocalRates()
        val storedFavorites: List<FavoriteEntity> = localDataSource.getFavorites()
        val response: CurrencyResponse
        val newRates: List<CurrencyEntity>
        val domainRates: CurrenciesLocal

        if (storedRates.isNotEmpty()) {
            // проверка данных на "свежесть" (5 минут)
            if (System.currentTimeMillis() - storedRates.first().timestamp.time >= 300000) {
                response = remoteDataSource.getLatestRates()
                newRates = CurrencyDtoMapper.mapResponseToDatabase(
                    response,
                    storedRates as MutableList<CurrencyEntity>
                )
                domainRates =
                    CurrencyDtoMapper.mapDatabaseToDomainModel(newRates, storedFavorites)
                localDataSource.updateRates(newRates)
            } else {
                domainRates =
                    CurrencyDtoMapper.mapDatabaseToDomainModel(storedRates, storedFavorites)
            }
        } else {
            response = remoteDataSource.getLatestRates()
            newRates = CurrencyDtoMapper.mapResponseToDatabase(response)
            domainRates = CurrencyDtoMapper.mapDatabaseToDomainModel(newRates, storedFavorites)
            localDataSource.addRates(newRates)
        }

        return domainRates
//        } catch (e: Exception) {
//            return null
//        }
    }

    suspend fun getHistory(): HistoryLocal {
        val storedHistory: List<HistoryEntity> = localDataSource.getAllHistoryEntries()

        return HistoryDtoMapper.mapDatabaseToDomainModel(storedHistory)
    }

    suspend fun addHistoryEntry(
        timestamp: Date,
        currency1: String,
        rate1: Double,
        value1: Double,
        currency2: String,
        rate2: Double,
        value2: Double,
        base: String,
    ) {
        localDataSource.addToHistory(
            HistoryEntity(
                0,
                timestamp,
                currency1,
                rate1,
                value1,
                currency2,
                rate2,
                value2,
                base,
            )
        )
    }

    suspend fun toggleFavorite(name: String, favState: Boolean) {
        if (favState) {
            val favEntry = localDataSource.getFavoriteByName(name)
            if (favEntry != null) {
                localDataSource.deleteFromFavorite(favEntry)
            }
        } else localDataSource.addToFavorite(FavoriteEntity(0, name))
    }
}

//class CurrencyRepository(private val currencyDAO: CurrencyApi) {
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun getRates(): CurrencyResponse {
//        return currencyDAO.getLatestRates()
//    }
//}