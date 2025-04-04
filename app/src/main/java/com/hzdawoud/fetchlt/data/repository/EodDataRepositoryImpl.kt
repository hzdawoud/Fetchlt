package com.hzdawoud.fetchlt.data.repository

import com.hzdawoud.fetchlt.data.remote.APIService
import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.utils.network.Either
import com.hzdawoud.fetchlt.utils.network.ErrorEntity
import com.hzdawoud.fetchlt.utils.network.NetworkHandler.toEither
import javax.inject.Inject

class EodDataRepositoryImpl @Inject constructor(private val apiService: APIService) :
    EodDataRepository {

    override suspend fun getEndOfDayData(symbols: String): Either<ErrorEntity, List<EodEntry>> {
        return apiService.getEndOfDayData(symbols).toEither(
            transform = { it.data.map { entry -> entry.toDomain() } },
            tag = TAG
        )
    }

    companion object {
        val TAG: String = EodDataRepositoryImpl::class.java.simpleName
    }
}