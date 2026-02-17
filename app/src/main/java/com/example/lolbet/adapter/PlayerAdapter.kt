package com.example.lolbet.adapter

import android.content.Context
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.data.Player
import com.example.lolbet.databinding.ItemPlayerBinding

class PlayerAdapter(
    private var players: List<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>() {
    // On garde en mémoire quelle position est agrandie (-1 = aucune)
    private var expandedPosition = -1
    class PlayerViewHolder(val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        // Initialisation du binding
        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        val context = holder.itemView.context
        val isExpanded = position == expandedPosition

        holder.binding.apply {
            tvPlayerName.text = player.name
            tvPlayerRank.text = player.rank.name
            val rankColor = ContextCompat.getColor(context, player.rank.color)
            tvPlayerRank.setTextColor(rankColor)

            tvPlayerId.text = context.getString(
                R.string.txt_player_id,
                player.id
            )

            tvPlayerLps.text = context.getString(
                R.string.txt_player_lps,
                player.lps
            )

            if (!player.hasOnlineBet) {
                tvPlayerHasBetOnline.visibility = View.GONE
                tvPlayerHasBetOnline.clearAnimation() // Bonne pratique : stopper l'anim

                val backgroundColor = ContextCompat.getColor(context, R.color.blue)
                vBorder.setBackgroundColor(backgroundColor)
            } else {
                tvPlayerHasBetOnline.visibility = View.VISIBLE
                val anim = AnimationUtils.loadAnimation(context, R.anim.blink_rec)
                tvPlayerHasBetOnline.startAnimation(anim)

                val backgroundColor = ContextCompat.getColor(context, R.color.yellow)
                vBorder.setBackgroundColor(backgroundColor)
            }

            // --- Logique d'expansion ---
            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            // Au clic sur la carte
            root.setOnClickListener {
                // Si on clique sur la même, on ferme. Sinon on ouvre la nouvelle.
                val prevExpanded = expandedPosition
                expandedPosition = if (isExpanded) -1 else position

                // Animation fluide
                TransitionManager.beginDelayedTransition(root)

                // On rafraîchit l'ancienne et la nouvelle position
                notifyItemChanged(prevExpanded)
                notifyItemChanged(position)
            }

            // --- Actions des boutons ---
            btnVoteWin.setOnClickListener { /* Votre logique de vote vert */ }
            btnVoteLose.setOnClickListener { /* Votre logique de vote rouge */ }
        }
    }

    override fun getItemCount(): Int = players.size

    fun submitList(newPlayers: List<Player>) {
        players = newPlayers
        notifyDataSetChanged()
    }
}