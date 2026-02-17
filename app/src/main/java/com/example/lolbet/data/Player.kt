package com.example.lolbet.data

data class Player(
    val id: Int,
    val name: String,
    val level: Int,
    val rank: Rank,
    val lps: Int,
    val hasOnlineBet: Boolean,
    val picture: String,
    val nbWonBets: Int,
    val nbLostBets: Int,
)