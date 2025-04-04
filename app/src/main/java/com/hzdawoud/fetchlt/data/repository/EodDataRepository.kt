package com.hzdawoud.fetchlt.data.repository

import com.hzdawoud.fetchlt.domain.model.EodEntry
import com.hzdawoud.fetchlt.utils.network.Either
import com.hzdawoud.fetchlt.utils.network.ErrorEntity

interface EodDataRepository {
    suspend fun getEndOfDayData(symbols: String): Either<ErrorEntity, List<EodEntry>>
}