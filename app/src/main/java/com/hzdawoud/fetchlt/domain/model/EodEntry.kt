package com.hzdawoud.fetchlt.domain.model

import java.util.UUID

data class EodEntry(
    val id: String = UUID.randomUUID().toString(),
    val open: Double,
    val high: Double,
    val low: Double,
    val close: Double,
    val volume: Double,
    val adjHigh: Double,
    val adjLow: Double,
    val adjClose: Double,
    val adjOpen: Double,
    val adjVolume: Double,
    val splitFactor: Double,
    val dividend: Double,
    val symbol: String,
    val exchange: String,
    val date: String
) {

    val priceChange: Double
        get() = close - open

    val percentChange: Double
        get() = (priceChange / open) * 100

    val isPositive: Boolean
        get() = priceChange >= 0

    val dividendYield: Double
        get() = if (dividend > 0) (dividend / close) * 100 else 0.0
}