package com.example.lolbet.utils

import android.content.Context
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum

fun Bet.isPlayerBet(): Boolean {
    return hasBetFor == BetForEnum.PLAYER_WINNING || hasBetFor == BetForEnum.PLAYER_LOSING
}

fun Bet.getFormattedTitle(context: Context): String {
    val formattedDate = DateHelper.formatBetDate(this.date)
    val secondParam = this.championship ?: this.player?.name
    return context.getString(R.string.txt_bet_championship_or_player_and_date, formattedDate, secondParam)
}