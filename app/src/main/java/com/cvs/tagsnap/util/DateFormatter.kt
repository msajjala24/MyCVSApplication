package com.cvs.tagsnap.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


private const val UTC_TIME_ZONE = "UTC"
private const val ISO_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
private const val LOCAL_TIME_ZONE = "yyyy-MM-dd hh:mm:ss a"
private const val EMPTY_STRING = ""

fun convertToLocalTime(utcTime: String): String {

    val utcFormat = SimpleDateFormat(ISO_TIME_FORMAT, Locale.getDefault()).apply {
        timeZone = TimeZone.getTimeZone(UTC_TIME_ZONE) // Specify UTC time zone
    }

    val date: Date = try {
        utcFormat.parse(utcTime) ?: return EMPTY_STRING
    } catch (ex: Exception) {
        return EMPTY_STRING
    }

    val localFormat = SimpleDateFormat(LOCAL_TIME_ZONE, Locale.getDefault()).apply {
        timeZone = TimeZone.getDefault()
    }
    return localFormat.format(date)
}