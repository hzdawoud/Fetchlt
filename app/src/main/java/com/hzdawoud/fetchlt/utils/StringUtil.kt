package com.hzdawoud.fetchlt.utils

import java.util.Locale

object StringUtil {

    // Formats the Double value to two decimal places
    fun Double.formatted(): String {
        return String.format(Locale.getDefault(), "%.2f", this)
    }
}