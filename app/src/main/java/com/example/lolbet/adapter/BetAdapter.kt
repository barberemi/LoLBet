package com.example.lolbet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.utils.DateHelper
import androidx.core.graphics.toColorInt
import com.example.lolbet.databinding.ItemLiveBetPlayerBinding
import com.example.lolbet.databinding.ItemLiveBetTeamBinding

class BetAdapter(private var bets: List<Bet>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_TEAM = 0
        private const val TYPE_PLAYER = 1
    }

    // ViewHolder pour les Teams
    class TeamViewHolder(val binding: ItemLiveBetTeamBinding) : RecyclerView.ViewHolder(binding.root)

    // ViewHolder pour les Players
    class PlayerViewHolder(val binding: ItemLiveBetPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        val bet = bets[position]
        // On détermine le type selon ton Enum ou la présence d'un nom de joueur
        return if (bet.hasBetFor == BetForEnum.PLAYER_WINNING || bet.hasBetFor == BetForEnum.PLAYER_LOSING) {
            TYPE_PLAYER
        } else {
            TYPE_TEAM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PLAYER -> {
                val binding = ItemLiveBetPlayerBinding.inflate(inflater, parent, false)
                PlayerViewHolder(binding)
            }
            else -> {
                val binding = ItemLiveBetTeamBinding.inflate(inflater, parent, false)
                TeamViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bet = bets[position]
        val context = holder.itemView.context

        when (holder) {
            is TeamViewHolder -> {
                bindTeamBet(holder.binding, bet, context)
            }
            is PlayerViewHolder -> {
                bindPlayerBet(holder.binding, bet, context)
            }
        }
    }

    private fun bindTeamBet(binding: ItemLiveBetTeamBinding, bet: Bet, context: Context) {
        with(binding) {
            // DATE
            val formattedDate = DateHelper.formatBetDate(bet.date)
            val secondParam = bet.championship ?: bet.player
            tvEventTitle.text = context.getString(
                R.string.txt_bet_championship_or_player_and_date,
                formattedDate,
                secondParam
            )
            // BET FOR
            val isVotedA = bet.hasBetFor == BetForEnum.TEAM_A_WINNING
            imgCheckA.visibility = if (isVotedA) View.VISIBLE else View.GONE
            tvVotesA.setTextColor(if (isVotedA) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val isVotedB = bet.hasBetFor == BetForEnum.TEAM_B_WINNING
            imgCheckB.visibility = if (isVotedB) View.VISIBLE else View.GONE
            tvVotesB.setTextColor(if (isVotedB) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val teamName = if (isVotedA) bet.teamA?.name else bet.teamB?.name
            tvBetStatus.text = context.getString(R.string.txt_bet_for, teamName)

            // NB BETS
            tvVotesA.text = context.getString(R.string.txt_nb_votes, bet.nbWinningBets)
            tvVotesB.text = context.getString(R.string.txt_nb_votes, bet.nbLosingBets)

            // LOGO & TEAM NAMES
            if (bet.teamA != null && bet.teamB != null) {
                imgTeamA.setImageResource(bet.teamA.logo)
                imgTeamB.setImageResource(bet.teamB.logo)
                tvTeamNameA.text = bet.teamA.name
                tvTeamNameB.text = bet.teamB.name
            }
        }
    }

    private fun bindPlayerBet(binding: ItemLiveBetPlayerBinding, bet: Bet, context: Context) {
        with(binding) {
            // DATE
            val formattedDate = DateHelper.formatBetDate(bet.date)
            val secondParam = bet.championship ?: bet.player
            tvEventTitle.text = context.getString(
                R.string.txt_bet_championship_or_player_and_date,
                formattedDate,
                secondParam
            )
            // BET FOR
            val isVotedA = bet.hasBetFor == BetForEnum.PLAYER_WINNING
            imgCheckA.visibility = if (isVotedA) View.VISIBLE else View.GONE
            tvVotesA.setTextColor(if (isVotedA) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val isVotedB = bet.hasBetFor == BetForEnum.PLAYER_LOSING
            imgCheckB.visibility = if (isVotedB) View.VISIBLE else View.GONE
            tvVotesB.setTextColor(if (isVotedB) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())

            val statusRes = if (isVotedA) R.string.txt_win_title else R.string.txt_lose_title
            tvBetStatus.text = context.getString(R.string.txt_bet_for, context.getString(statusRes))

            // NB BETS
            tvVotesA.text = context.getString(R.string.txt_nb_votes, bet.nbWinningBets)
            tvVotesB.text = context.getString(R.string.txt_nb_votes, bet.nbLosingBets)

            // LOGO & TEAM NAMES
            if (bet.player != null) {
                sivPlayerAvatar.setImageResource(R.drawable.xadxxx)
                tvTeamNameA.text = context.getString(R.string.txt_win_title)
                tvTeamNameB.text = context.getString(R.string.txt_lose_title)
            }
        }
    }

    override fun getItemCount() = bets.size

    fun updateData(newBets: List<Bet>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = bets.size
            override fun getNewListSize() = newBets.size

            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean {
                return bets[oldPos].id == newBets[newPos].id // On compare les IDs
            }

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
                return bets[oldPos] == newBets[newPos] // On compare le contenu
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.bets = newBets
        diffResult.dispatchUpdatesTo(this) // Anime automatiquement les changements !
    }
}