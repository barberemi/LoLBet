package com.example.lolbet.data

data class Bet(
    val id: Int,
    val teamA: Team?,
    val teamB: Team?,
    val player: String?,
    val nbWinningBets: Int,
    val nbLosingBets: Int,
    val status: BetStatusEnum,
    val date: Long,
    val hasBetFor: BetForEnum = BetForEnum.PLAYER_LOSING,
    val championship: String?
)