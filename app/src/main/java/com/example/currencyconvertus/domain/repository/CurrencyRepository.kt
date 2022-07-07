package com.example.currencyconvertus.domain.repository

import com.example.currencyconvertus.data_local.entity.CurrencyEntity
import com.example.currencyconvertus.data_local.entity.FavoriteEntity
import com.example.currencyconvertus.data_remote.CurrencyResponse
import com.example.currencyconvertus.data_source.LocalDataSource
import com.example.currencyconvertus.data_source.RemoteDataSource
import com.example.currencyconvertus.domain.mapper.CurrencyDtoMapper
import com.example.currencyconvertus.domain.model.CurrenciesLocal
import java.util.*


class CurrencyRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    suspend fun getCurrencies(): CurrenciesLocal? {
        try {
            val storedRates: List<CurrencyEntity>? = localDataSource.getLocalRates()
            val storedFavorites: List<FavoriteEntity>? = localDataSource.getFavorites()
            val response: CurrencyResponse
            val newRates: List<CurrencyEntity>
            val domainRates: CurrenciesLocal

            if (storedRates != null) {
                // проверка данных на "свежесть" (5 минут)
                if (Date().time - storedRates.first().timestamp.time >= 300000) {
                    response = remoteDataSource.getLatestRates()
                    newRates = CurrencyDtoMapper.mapResponseToDatabase(
                        response,
                        storedRates as MutableList<CurrencyEntity>
                    )
                    domainRates =
                        CurrencyDtoMapper.mapDatabaseToDomainModel(newRates, storedFavorites)
                    localDataSource.udateRates(newRates)
                } else {
                    domainRates =
                        CurrencyDtoMapper.mapDatabaseToDomainModel(storedRates, storedFavorites)
                }
            } else {
                response = remoteDataSource.getLatestRates()
                newRates = CurrencyDtoMapper.mapResponseToDatabase(response)
                domainRates = CurrencyDtoMapper.mapDatabaseToDomainModel(newRates, storedFavorites)
                localDataSource.udateRates(newRates)
            }

            return domainRates
        } catch (e: Exception) {
            return null
        }
    }
}

//class CurrencyRepository(private val currencyDAO: CurrencyApi) {
//    @Suppress("RedundantSuspendModifier")
//    @WorkerThread
//    suspend fun getRates(): CurrencyResponse {
//        return currencyDAO.getLatestRates()
//    }
//}