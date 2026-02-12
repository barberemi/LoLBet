package com.example.lolbet.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateHelper {
    private val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    private val fullFormatter = DateTimeFormatter.ofPattern("d MMM HH'h'mm", Locale.FRENCH)
    private val timeFormatter = DateTimeFormatter.ofPattern("HH'h'mm")

    fun formatBetDate(dateString: String?): String {
        if (dateString == null) return ""
        return try {
            val date = LocalDateTime.parse(dateString, inputFormatter)
            val now = LocalDateTime.now()

            if (date.toLocalDate().isEqual(now.toLocalDate())) {
                date.format(timeFormatter)
            } else {
                date.format(fullFormatter)
            }
        } catch (e: Exception) {
            dateString // Sécurité : si le format change, on affiche la string brute
        }
    }
}