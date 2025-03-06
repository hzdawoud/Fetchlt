package com.hzdawoud.fetchlt.data.model

import com.hzdawoud.fetchlt.domain.model.EodEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

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
        id = UUID.randomUUID().toString(), // generate a unique id for each conversion
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume,
        adjHigh = adjHigh,
        adjLow = adjLow,
        adjClose = adjClose,
        adjOpen = adjOpen,
        adjVolume = adjVolume,
        splitFactor = splitFactor,
        dividend = dividend,
        symbol = symbol,
        exchange = exchange,
        date = date
    )
}