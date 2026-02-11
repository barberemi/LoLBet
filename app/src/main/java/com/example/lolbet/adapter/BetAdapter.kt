package com.example.lolbet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.databinding.ItemLiveBetBinding

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
            // images
            if (bet.teamA !== null && bet.teamB !== null) {
                sivPlayerAvatar.visibility = View.GONE
                imgTeamA.visibility = View.VISIBLE
                imgTeamB.visibility = View.VISIBLE
                tvVs.visibility = View.VISIBLE
            } else {
                sivPlayerAvatar.visibility = View.VISIBLE
                imgTeamA.visibility = View.GONE
                imgTeamB.visibility = View.GONE
                tvVs.visibility = View.GONE
            }
            // bet for
            when (bet.hasBetFor) {
                BetForEnum.TEAM_A_WINNING -> {
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, bet.teamA)
                }
                BetForEnum.TEAM_B_WINNING -> {
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, bet.teamB)
                }
                BetForEnum.PLAYER_WINNING -> {
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, context.getString(R.string.txt_win))
                }
                BetForEnum.PLAYER_LOSING -> {
                    tvBetStatus.text = context.getString(R.string.txt_bet_for, context.getString(R.string.txt_lose))
                }
            }
//            tvTeam1.text = bet.team1
//            tvTeam2.text = bet.team2
//            tvAmount.text = "${bet.amount} €"
//            tvStatus.text = bet.status.name
//            tvBetStatus.text = getString(R.string.txt_level, user.level)
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