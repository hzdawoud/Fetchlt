package com.hzdawoud.fetchlt.data.model

import com.hzdawoud.fetchlt.domain.model.EodEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EodEntryDto(
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    @SerialName("adj_high") val adjHigh: Double,
    @SerialName("adj_low") val adjLow: Double,
    @SerialName("adj_close") val adjClose: Double,
    @SerialName("adj_open") val adjOpen: Double,
    @SerialName("adj_volume") val adjVolume: Double,
    @SerialName("split_factor") val splitFactor: Double,
    val dividend: Double,
    val symbol: String,
    val exchange: String,
    val date: String
) {
    fun toDomain() = EodEntry(
        open, high, low, close, volume,
        adjHigh, adjLow, adjClose, adjOpen, adjVolume,
        splitFactor, dividend, symbol, exchange, date
    )
}