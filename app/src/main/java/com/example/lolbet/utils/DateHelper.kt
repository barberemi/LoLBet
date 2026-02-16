package com.example.lolbet.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateHelper {
    // On garde tes formateurs pour l'affichage (Output)
    private val fullFormatter = DateTimeFormatter.ofPattern("d MMM HH'h'mm", Locale.FRENCH)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm")

    fun formatBetDate(timestamp: Long?): String {
        if (timestamp == null || timestamp == 0L) return ""

        return try {
            // Conversion du Timestamp Long en LocalDateTime
            val date = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
            )
            val now = LocalDateTime.now()

            // Logique de comparaison : aujourd'hui vs autre jour
            if (date.toLocalDate().isEqual(now.toLocalDate())) {
                date.format(timeFormatter)
            } else {
                date.format(fullFormatter)
            }
        } catch (e: Exception) {
            ""
        }
    }
}