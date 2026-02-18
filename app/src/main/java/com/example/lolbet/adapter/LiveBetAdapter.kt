package com.example.lolbet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.data.Bet
import com.example.lolbet.databinding.ItemLiveBetPlayerBinding
import com.example.lolbet.databinding.ItemLiveBetTeamBinding
import com.example.lolbet.holder.LivePlayerViewHolder
import com.example.lolbet.holder.LiveTeamViewHolder
import com.example.lolbet.utils.isPlayerBet

class LiveBetAdapter(private var bets: List<Bet>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TEAM = 0
        private const val TYPE_PLAYER = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (bets[position].isPlayerBet()) TYPE_PLAYER else TYPE_TEAM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_PLAYER -> LivePlayerViewHolder(ItemLiveBetPlayerBinding.inflate(inflater, parent, false))
            else -> LiveTeamViewHolder(ItemLiveBetTeamBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val bet = bets[position]
        when (holder) {
            is LiveTeamViewHolder -> holder.bind(bet)
            is LivePlayerViewHolder -> holder.bind(bet)
        }
    }

    override fun getItemCount() = bets.size

    fun updateData(newBets: List<Bet>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = bets.size
            override fun getNewListSize(): Int = newBets.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                // On compare les identifiants uniques
                return bets[oldItemPosition].id == newBets[newItemPosition].id
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = bets[oldItemPosition]
                val newItem = newBets[newItemPosition]

                // On vérifie si les scores du joueur à l'intérieur du pari ont changé
                val areScoresSame = oldItem.player?.nbWonBets == newItem.player?.nbWonBets &&
                        oldItem.player?.nbLostBets == newItem.player?.nbLostBets

                // On vérifie si le statut du pari a changé
                val isStatusSame = oldItem.status == newItem.status

                // On ne retourne TRUE que si absolument TOUT est identique
                return areScoresSame && isStatusSame && oldItem == newItem
            }
        })

        this.bets = newBets
        notifyDataSetChanged()
    }
}