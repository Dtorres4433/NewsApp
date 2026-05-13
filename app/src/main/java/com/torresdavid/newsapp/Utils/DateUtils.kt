package com.torresdavid.newsapp.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    private val displayFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(isoDate: String?): String {
        if (isoDate.isNullOrBlank()) return "N/A"
        return try {
            val zonedDateTime = ZonedDateTime.parse(isoDate)
            val localDateTime = zonedDateTime.withZoneSameInstant(ZoneId.systemDefault())
            localDateTime.format(displayFormatter)
        } catch (e: DateTimeParseException) {
            "N/A"
        }
    }
}