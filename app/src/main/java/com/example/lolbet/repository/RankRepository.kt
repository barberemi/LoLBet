package com.example.lolbet.repository

import com.example.lolbet.R
import com.example.lolbet.data.Rank

object RankRepository {
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

    fun getAllRanks(): List<Rank> = ranks

    fun getRankByRps(rps: Int): Rank? {
        val eligibleRanks = ranks.filter { it.minRps <= rps }
        return eligibleRanks.maxByOrNull { it.minRps } ?: ranks.minByOrNull { it.minRps }
    }

    fun getNextRank(rps: Int): Rank? {
        val currentRank = getRankByRps(rps) ?: return null
        return ranks.firstOrNull { it.order == currentRank.order + 1 }
    }
}