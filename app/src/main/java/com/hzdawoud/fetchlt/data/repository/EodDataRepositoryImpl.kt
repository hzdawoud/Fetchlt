package com.hzdawoud.fetchlt.data.repository

import android.util.Log
import com.hzdawoud.fetchlt.data.remote.APIService
import com.hzdawoud.fetchlt.domain.model.EodResponse
import com.hzdawoud.fetchlt.utils.network.NetworkHandler.toResource
import com.hzdawoud.fetchlt.utils.network.Resource
import javax.inject.Inject

class EodDataRepositoryImpl @Inject constructor(private val apiService: APIService) :
    EodDataRepository {

    init {
        Log.d(TAG, "init")
    }

    override suspend fun getEndOfDayData(symbols: String): Resource<EodResponse> {
        return apiService.getEndOfDayData(symbols).toResource(
            transform = { it.toDomain() },
            tag = TAG
        )
    }

    companion object {
        val TAG: String = EodDataRepositoryImpl::class.java.simpleName
    }
}