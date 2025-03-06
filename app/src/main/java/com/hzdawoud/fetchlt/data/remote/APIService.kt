package com.hzdawoud.fetchlt.data.remote

import com.hzdawoud.fetchlt.data.model.EodResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("eod")
    suspend fun getEndOfDayData(
        @Query("symbols") symbols: String = "AAPL,MSFT"
    ): Response<EodResponseDto>
}