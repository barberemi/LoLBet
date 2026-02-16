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
    val bets: List<Bet> = emptyList(),
) {
    fun getPendingBetsSorted() = bets
        .filter { it.status == BetStatusEnum.PENDING }
        .sortedByDescending { it.date }

    fun getLastNonPendingBet() = bets
        .filter { it.status != BetStatusEnum.PENDING }
        .maxByOrNull { it.date }
}
