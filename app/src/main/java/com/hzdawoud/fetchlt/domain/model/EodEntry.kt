package com.hzdawoud.fetchlt.domain.model

data class EodEntry(
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
)