package com.hzdawoud.fetchlt.utils

object CoilUtil {

    fun getStockLogoUrl(symbol: String): String {
        // You could use a real API like:
        // For demo purposes, using a placeholder:
        return "https://logo.clearbit.com/$symbol.com"
    }
}