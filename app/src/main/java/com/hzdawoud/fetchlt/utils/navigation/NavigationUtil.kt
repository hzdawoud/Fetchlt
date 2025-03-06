package com.hzdawoud.fetchlt.utils.navigation

sealed class Screen(val route: String) {
    data object StockList : Screen("stockList")
    data object StockDetail : Screen("stockDetail/{symbol}") {
        fun createRoute(symbol: String): String = "stockDetail/$symbol"
    }
}