package com.hzdawoud.fetchlt.utils

object CoilUtil {

    fun getStockLogoUrl(symbol: String): String {
        // You could use a real API or store the images as vectors locally.
        // For demo purposes, using a placeholder:
        return "https://logo.clearbit.com/$symbol.com"
    }
}