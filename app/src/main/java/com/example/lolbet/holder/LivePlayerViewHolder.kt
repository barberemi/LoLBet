package com.example.lolbet.holder

import android.view.View
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.databinding.ItemLiveBetPlayerBinding
import com.example.lolbet.utils.getFormattedTitle

class LivePlayerViewHolder(private val binding: ItemLiveBetPlayerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bet: Bet) {
        val context = itemView.context
        binding.apply {
            tvEventTitle.text = bet.getFormattedTitle(context)

            val isVotedWin = bet.hasBetFor == BetForEnum.PLAYER_WINNING
            val isVotedLose = bet.hasBetFor == BetForEnum.PLAYER_LOSING

            imgCheckA.visibility = if (isVotedWin) View.VISIBLE else View.GONE
            tvVotesA.setTextColor(if (isVotedWin) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            imgCheckB.visibility = if (isVotedLose) View.VISIBLE else View.GONE
            tvVotesB.setTextColor(if (isVotedLose) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val statusRes = if (isVotedWin) R.string.txt_win_title else R.string.txt_lose_title
            tvBetStatus.text = context.getString(R.string.txt_bet_for, context.getString(statusRes))

            tvVotesA.text = context.getString(R.string.txt_nb_votes, bet.nbWinningBets)
            tvVotesB.text = context.getString(R.string.txt_nb_votes, bet.nbLosingBets)

            sivPlayerAvatar.setImageResource(R.drawable.xadxxx)
            tvTeamNameA.text = context.getString(R.string.txt_win_title)
            tvTeamNameB.text = context.getString(R.string.txt_lose_title)
        }
    }
}