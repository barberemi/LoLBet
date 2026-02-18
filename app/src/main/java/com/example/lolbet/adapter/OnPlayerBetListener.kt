package com.example.lolbet.adapter

import com.example.lolbet.data.Player

interface OnPlayerBetListener {
    fun onBetClick(player: Player, isWinBet: Boolean)
}