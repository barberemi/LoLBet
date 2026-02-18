package com.example.lolbet.holder

import android.view.View
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.databinding.ItemLiveBetTeamBinding
import com.example.lolbet.utils.getFormattedTitle

class LiveTeamViewHolder(private val binding: ItemLiveBetTeamBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bet: Bet) {
        val context = itemView.context
        binding.apply {
            tvEventTitle.text = bet.getFormattedTitle(context)

            val isVotedA = bet.hasBetFor == BetForEnum.TEAM_A_WINNING
            val isVotedB = bet.hasBetFor == BetForEnum.TEAM_B_WINNING

            // Utilisation de votre couleur personnalisée #3F51B5FF
            imgCheckA.visibility = if (isVotedA) View.VISIBLE else View.GONE
            tvVotesA.setTextColor(if (isVotedA) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            imgCheckB.visibility = if (isVotedB) View.VISIBLE else View.GONE
            tvVotesB.setTextColor(if (isVotedB) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val teamName = if (isVotedA) bet.teamA?.name else bet.teamB?.name
            tvBetStatus.text = context.getString(R.string.txt_bet_for, teamName)

            tvVotesA.text = context.getString(R.string.txt_nb_votes, bet.nbWinningBets)
            tvVotesB.text = context.getString(R.string.txt_nb_votes, bet.nbLosingBets)

            bet.teamA?.let { imgTeamA.setImageResource(it.logo); tvTeamNameA.text = it.name }
            bet.teamB?.let { imgTeamB.setImageResource(it.logo); tvTeamNameB.text = it.name }
        }
    }
}