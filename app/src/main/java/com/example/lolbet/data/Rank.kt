package com.example.lolbet.data

import androidx.annotation.ColorRes

data class Rank(
    val id: Int,
    val name: String,
    val icon: String,
    @ColorRes val color: Int,
    val minRps: Int,
    val order: Int
)
