package com.example.lolbet.data

data class Bet(
    val id: Int,
    val teamA: String?,
    val teamB: String?,
    val player: String?, // Player ?
    val wins: Int,
    val loses: Int,
    val status: BetStatus,
    val date: String
)