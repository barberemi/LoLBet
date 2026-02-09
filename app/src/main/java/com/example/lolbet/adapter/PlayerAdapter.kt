package com.example.lolbet.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.data.Player
import com.example.lolbet.R

class PlayerAdapter(
    private var players: List<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvPlayerName)
        val tvHasBetOnline: TextView = itemView.findViewById(R.id.tvPlayerHasBetOnline)
        val tvId: TextView = itemView.findViewById(R.id.tvPlayerId)
        val tvRank: TextView = itemView.findViewById(R.id.tvPlayerRank)
        val tvLps: TextView = itemView.findViewById(R.id.tvPlayerLps)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]

        holder.tvName.text = player.name
        holder.tvRank.text = player.rank
        holder.tvId.text = holder.itemView.context.getString(
            R.string.txt_player_id,
            player.id
        )
        holder.tvLps.text = holder.itemView.context.getString(
            R.string.txt_player_lps,
            player.lps
        )
        if (!player.hasOnlineBet) {
            holder.tvHasBetOnline.visibility = View.GONE
        } else {
            val anim = AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.blink_rec
            )
            holder.tvHasBetOnline.startAnimation(anim)
            holder.tvHasBetOnline.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int = players.size

    fun submitList(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}