package com.nsa.ui.ext

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.util.Log
import java.sql.Timestamp
import java.util.Date
import java.util.Locale


fun Timestamp.getFormatedText(outputFormat:String): String {
    val timestampFormat = "yyyy-mm-dd hh:mm:ss.fffffffff"

    val dateFormatter = SimpleDateFormat(outputFormat, Locale.getDefault())
    dateFormatter.timeZone = TimeZone.getTimeZone("GMT")

    val parser = SimpleDateFormat(timestampFormat, Locale.getDefault())
    parser.timeZone = TimeZone.getTimeZone("GMT")

    try {
        val date = Date(this.time)
        dateFormatter.timeZone = TimeZone.getDefault()
        return dateFormatter.format(date)
    } catch (e: Exception) {
        // Handle parsing error
        Log.d("check_date", "getFormatedText: ${e.localizedMessage}")
        e.printStackTrace()
    }

    // If parsing fails, return the original timestamp
    return toString()
}