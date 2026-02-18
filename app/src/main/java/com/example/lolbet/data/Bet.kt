package com.example.lolbet.data

data class Bet(
    val id: Int,
    val teamA: Team? = null,
    val teamB: Team? = null,
    val player: Player? = null,
    val nbWinningBets: Int = 0,
    val nbLosingBets: Int = 0,
    val status: BetStatusEnum = BetStatusEnum.PENDING,
    val date: Long,
    val hasBetFor: BetForEnum = BetForEnum.PLAYER_LOSING,
    val championship: String? = null
)