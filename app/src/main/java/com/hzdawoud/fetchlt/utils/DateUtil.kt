package com.hzdawoud.fetchlt.utils

import com.hzdawoud.fetchlt.utils.Formats.UI_DATE_FORMAT
import com.hzdawoud.fetchlt.utils.Formats.UTC_FORMAT
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtil {

    /** Formats a given date string from UTC format (e.g., "yyyy-MM-dd'T'HH:mm:ssZ")
    to the specified output format (default: "dd MMM yyyy") **/
    fun formattedDate(inputDate: String, outputFormat: String = UI_DATE_FORMAT): String {
        val inputFormat = SimpleDateFormat(UTC_FORMAT, Locale.getDefault())

        val date: Date = inputFormat.parse(inputDate) ?: return inputDate

        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        return outputDateFormat.format(date)
    }
}

object Formats {
    const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
    const val UI_DATE_FORMAT = "dd MMM yyyy"
}


