package com.example.lolbet.data

data class Bet(
    val id: Int,
    val teamA: String?,
    val teamB: String?,
    val player: String?, // Player ?
    val nbWinningBets: Int,
    val nbLosingBets: Int,
    val status: BetStatusEnum,
    val date: String,
    val hasBetFor: BetForEnum = BetForEnum.PLAYER_LOSING,
    val championship: String?
)