package com.example.lolbet.data

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val password: String,
    val picture: String,
    val nbBetWin: Int,
    val nbBetLost: Int,
    val rps: Int,
    val level: Int,
    val rank: String,
    val bets: List<Bet> = emptyList(),
)
