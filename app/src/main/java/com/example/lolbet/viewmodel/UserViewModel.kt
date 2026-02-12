package com.example.lolbet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.data.BetStatusEnum
import com.example.lolbet.data.Rank
import com.example.lolbet.data.User
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserViewModel: ViewModel() {
    private val betDateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())

    val userData = MutableLiveData<User>()
    val ranks = listOf(
        Rank(1, "Iron III", "", R.color.rank_iron, 0, 1),
        Rank(2, "Iron II", "", R.color.rank_iron, 100, 2),
        Rank(3, "Iron I", "", R.color.rank_iron, 200, 3),

        Rank(4, "Bronze III", "", R.color.rank_bronze, 300, 4),
        Rank(5, "Bronze II", "", R.color.rank_bronze, 400, 5),
        Rank(6, "Bronze I", "", R.color.rank_bronze, 500, 6),

        Rank(7, "Silver III", "", R.color.rank_silver, 600, 7),
        Rank(8, "Silver II", "", R.color.rank_silver, 700, 8),
        Rank(9, "Silver I", "", R.color.rank_silver, 800, 9),

        Rank(10, "Gold III", "", R.color.rank_gold, 900, 10),
        Rank(11, "Gold II", "", R.color.rank_gold, 1000, 11),
        Rank(12, "Gold I", "", R.color.rank_gold, 1100, 12),

        Rank(13, "Platinum III", "", R.color.rank_platinum, 1200, 13),
        Rank(14, "Platinum II", "", R.color.rank_platinum, 1300, 14),
        Rank(15, "Platinum I", "", R.color.rank_platinum, 1400, 15),

        Rank(16, "Emeraud III", "", R.color.rank_emeraud, 1500, 16),
        Rank(17, "Emeraud II", "", R.color.rank_emeraud, 1600, 17),
        Rank(18, "Emeraud I", "", R.color.rank_emeraud, 1700, 18),

        Rank(19, "Diamond III", "", R.color.rank_diamond, 1800, 19),
        Rank(20, "Diamond II", "", R.color.rank_diamond, 1900, 20),
        Rank(21, "Diamond I", "", R.color.rank_diamond, 2000, 21),

        Rank(22, "Master", "", R.color.rank_master, 2100, 22),

        Rank(23, "Grandmaster", "", R.color.rank_grandmaster, 2200, 23),

        Rank(24, "Challenger", "", R.color.rank_challenger, 2300, 24),
    )

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
            bets = listOf(
                Bet(1,
                    "G2 ESPORT",
                    "FNATICS",
                    null,
                    100,
                    44,
                    BetStatusEnum.PENDING,
                    "06/02/2026 12:00:00",
                    BetForEnum.TEAM_A_WINNING,
                    "LEC"
                ),
                Bet(2,
                    null,
                    null,
                    "XadXXX",
                    200,
                    23,
                    BetStatusEnum.PENDING,
                    "07/02/2026 12:00:00",
                    BetForEnum.PLAYER_WINNING,
                    null
                ),
                Bet(3,
                    "G2 ESPORT",
                    "FNATICS",
                    null,
                    300,
                    58,
                    BetStatusEnum.PENDING,
                    "08/02/2026 12:00:00",
                    BetForEnum.TEAM_B_WINNING,
                    "LEC"
                ),
                Bet(4,
                    "G2 ESPORT",
                    "FNATICS",
                    null,
                    10,
                    422,
                    BetStatusEnum.WON,
                    "06/02/2026 15:00:00",
                    BetForEnum.TEAM_A_WINNING,
                    "LEC"
                ),
                Bet(5,
                    null,
                    null,
                    "XadXXX",
                    55,
                    888,
                    BetStatusEnum.LOST,
                    "06/02/2026 17:00:00",
                    BetForEnum.PLAYER_LOSING,
                    null
                ),
            )
        )
    }

    fun getLastNonPendingBet(user: User): Bet? {
        return user.bets
            .filter { it.status != BetStatusEnum.PENDING }
            .maxByOrNull { bet ->
                try {
                    val date: Date = betDateFormatter.parse(bet.date) ?: Date(0)
                    date.time
                } catch (e: Exception) {
                    0L
                }
            }
    }

    fun getRank(rps: Int): Rank? {
        // on garde seulement les ranks dont minRps <= rps
        val eligibleRanks = ranks.filter { it.minRps <= rps }

        // si aucun éligible, on retourne le tout premier rank
        if (eligibleRanks.isEmpty()) return ranks.minByOrNull { it.minRps }

        // on prend celui avec le minRps le plus élevé
        return eligibleRanks.maxByOrNull { it.minRps }
    }

    fun getRpsNextRank(rps: Int): Rank? {
        // rang actuel en fonction du rps
        val currentRank = getRank(rps) ?: return ranks.minByOrNull { it.order }

        // rang juste après
        val nextOrder = currentRank.order + 1

        // si pas de rang avec cet order (donc on est au max), on renvoie null
        return ranks.firstOrNull { it.order == nextOrder }
    }
}