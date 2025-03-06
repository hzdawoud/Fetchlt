package com.hzdawoud.fetchlt.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtil {
    fun formattedDate(inputDate: String, outputFormat: String = "dd MMM yyyy"): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date: Date = inputFormat.parse(inputDate) ?: return inputDate

        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return outputDateFormat.format(date)
    }
}


