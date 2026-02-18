package com.example.lolbet.data

data class Player(
    val id: Int,
    val name: String,
    val level: Int = 0,
    val rank: Rank,
    val lps: Int = 0,
    val hasOnlineBet: Boolean = false,
    val picture: String,
    val nbWonBets: Int = 0,
    val nbLostBets: Int = 0,
)