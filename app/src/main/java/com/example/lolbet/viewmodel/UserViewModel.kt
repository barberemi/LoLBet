package com.example.lolbet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetStatus
import com.example.lolbet.data.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserViewModel: ViewModel() {
    private val betDateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    val userData = MutableLiveData<User>()

    init {
        loadMockData()
    }

    private fun loadMockData() {
        // On simule ici la "réponse" de votre future base de données
        userData.value = User(
            id = 777,
            name = "Cromelu",
            email = "contact@exemple.com",
            password = "password123",
            picture = "cromelu",
            nbBetWin = 10,
            nbBetLost = 5,
            rps = 1500,
            level = 5,
            rank = "Gold I",
            bets = listOf(
                Bet(1, "G2 ESPORT", "FNATICS", null, 100, 44, BetStatus.PENDING, "06/02/2026 12:00:00"),
                Bet(2, null, null, "XadXXX", 200, 23, BetStatus.PENDING, "07/02/2026 12:00:00"),
                Bet(3, "G2 ESPORT", "FNATICS", null, 300, 58, BetStatus.PENDING, "08/02/2026 12:00:00"),
                Bet(4, "G2 ESPORT", "FNATICS", null, 10, 422, BetStatus.WON, "06/02/2026 15:00:00"),
                Bet(5, null, null, "XadXXX", 55, 888, BetStatus.LOST, "06/02/2026 17:00:00"),
            )
        )
    }

    fun getLastNonPendingBet(user: User): Bet? {
        return user.bets
            .filter { it.status != BetStatus.PENDING }
            .maxByOrNull { bet ->
                try {
                    val date: Date = betDateFormatter.parse(bet.date) ?: Date(0)
                    date.time
                } catch (e: Exception) {
                    0L
                }
            }
    }
}