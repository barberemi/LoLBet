package com.example.lolbet.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.databinding.ItemLiveBetBinding
import com.example.lolbet.utils.DateHelper
import androidx.core.graphics.toColorInt

class BetAdapter(private var bets: List<Bet>) : RecyclerView.Adapter<BetAdapter.BetViewHolder>() {

    class BetViewHolder(val binding: ItemLiveBetBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetViewHolder {
        val binding = ItemLiveBetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BetViewHolder, position: Int) {
        val bet = bets[position]
        val context = holder.binding.root.context

        with(holder.binding) {
            // Championship + date
            val formattedDate = DateHelper.formatBetDate(bet.date)
            val secondParam = bet.championship ?: bet.player
            tvEventTitle.text = context.getString(
                R.string.txt_bet_championship_or_player_and_date,
                formattedDate,
                secondParam
            )
            // bet for
            // --- ÉQUIPE A ---
            val isVotedA = bet.hasBetFor == BetForEnum.TEAM_A_WINNING
            // Visibilité du check et fond avec bordure
            imgCheckA.visibility = if (isVotedA) View.VISIBLE else View.GONE
            // Couleur du texte : Cyan si voté, votre Bleu #3F51B5FF sinon
            tvVotesA.setTextColor(if (isVotedA) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())
            // --- ÉQUIPE B ---
            val isVotedB = bet.hasBetFor == BetForEnum.TEAM_B_WINNING
            // Visibilité du check et fond avec bordure
            imgCheckB.visibility = if (isVotedB) View.VISIBLE else View.GONE
            // Couleur du texte : Cyan si voté, votre Bleu #3F51B5FF sinon
            tvVotesB.setTextColor(if (isVotedB) "#00FFFF".toColorInt() else "#3F51B5FF".toColorInt())
            when (bet.hasBetFor) {
                BetForEnum.TEAM_A_WINNING, BetForEnum.TEAM_B_WINNING -> {
                    // Affichage pour les équipes
                    areaVoteA.visibility = View.VISIBLE
                    areaVoteB.visibility = View.VISIBLE
                    flVs.visibility = View.VISIBLE
                    sivPlayerAvatar.visibility = View.GONE
                    tvVotesA.text = context.getString(R.string.txt_nb_votes, bet.nbWinningBets)
                    tvVotesB.text = context.getString(R.string.txt_nb_votes, bet.nbLosingBets)

                    val teamName = if (bet.hasBetFor == BetForEnum.TEAM_A_WINNING) bet.teamA?.name else bet.teamB?.name
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, teamName)

                    if (bet.teamA != null && bet.teamB != null) {
                        imgTeamA.setImageResource(bet.teamA.logo)
                        imgTeamB.setImageResource(bet.teamB.logo)
                        tvTeamNameA.text = bet.teamA.name
                        tvTeamNameB.text = bet.teamB.name
                    }
                }
                BetForEnum.PLAYER_WINNING, BetForEnum.PLAYER_LOSING -> {
                    // On cache tout ce qui concerne les équipes
                    areaVoteA.visibility = View.GONE
                    areaVoteB.visibility = View.GONE
                    flVs.visibility = View.GONE

                    // On affiche l'avatar du joueur
                    sivPlayerAvatar.visibility = View.VISIBLE

                    val statusRes = if (bet.hasBetFor == BetForEnum.PLAYER_WINNING) R.string.txt_win else R.string.txt_lose
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, context.getString(statusRes))
                }
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