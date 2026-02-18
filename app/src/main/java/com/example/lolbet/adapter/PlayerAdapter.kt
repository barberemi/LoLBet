package com.example.lolbet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.data.Player
import com.example.lolbet.databinding.ItemPlayerBinding
import com.example.lolbet.holder.BetPlayerViewHolder

class PlayerAdapter(
    private var players: List<Player>,
    private val listener: OnPlayerBetListener
) : RecyclerView.Adapter<BetPlayerViewHolder>() {

    private var expandedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetPlayerViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BetPlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BetPlayerViewHolder, position: Int) {
        val player = players[position]
        val isExpanded = position == expandedPosition

        holder.bind(player, isExpanded, listener) {
            val prevExpanded = expandedPosition
            expandedPosition = if (isExpanded) -1 else position

            notifyItemChanged(prevExpanded)
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = players.size

    fun submitList(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}