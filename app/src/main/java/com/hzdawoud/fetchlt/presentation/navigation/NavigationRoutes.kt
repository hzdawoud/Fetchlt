package com.hzdawoud.fetchlt.presentation.navigation

sealed class Screen(val route: String) {
    data object StockList : Screen("stockList")
    data object StockDetail : Screen("stockDetail/{id}") {
        fun createRoute(id: String): String = "stockDetail/$id"
    }
}