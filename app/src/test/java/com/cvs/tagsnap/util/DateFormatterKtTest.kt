package com.cvs.tagsnap.util

import junit.framework.TestCase.assertEquals
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class DateFormatterKtTest {

    @Test
    fun testConvertToLocalTime_validUTC() {
        val utcTime = "2024-03-15T10:00:00Z"
        val expectedLocalTime = "2024-03-15 05:00:00 AM"
        val actualLocalTime = convertToLocalTime(utcTime)
        Assert.assertEquals(expectedLocalTime, actualLocalTime)
    }

    @Test
    fun testConvertToLocalTime_invalidFormat() {
        val utcTime = "2024-03-15T10:00:00"
        val result = convertToLocalTime(utcTime)
        Assert.assertEquals("", result)
    }

    @Test
    fun `handles edge case - midnight`() {
        val utcTime = "2025-01-19T00:00:00Z"
        val expected = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault())
            .apply { timeZone = TimeZone.getDefault() }
            .format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                    .apply { timeZone = TimeZone.getTimeZone("UTC") }
                    .parse(utcTime)!!
            )

        // Assert
        assertEquals(expected, convertToLocalTime(utcTime))
    }

    @Test
    fun `empty UTC time returns empty string`() {
        // Empty input
        val emptyTime = ""
        // Assert
        assertEquals("", convertToLocalTime(emptyTime))
    }

}