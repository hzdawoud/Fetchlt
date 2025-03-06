package com.hzdawoud.fetchlt.data.repository

import com.hzdawoud.fetchlt.domain.model.EodResponse
import com.hzdawoud.fetchlt.utils.network.Resource

interface EodDataRepository {
    suspend fun getEndOfDayData(symbols: String): Resource<EodResponse>
}