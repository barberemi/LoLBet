package com.example.lolbet.holder

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.R
import com.example.lolbet.adapter.OnPlayerBetListener
import com.example.lolbet.data.Player
import com.example.lolbet.databinding.ItemPlayerBinding
import com.example.lolbet.utils.formatBetButtonText

class BetPlayerViewHolder(private val binding: ItemPlayerBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(player: Player, isExpanded: Boolean, listener: OnPlayerBetListener, onExpandClick: () -> Unit) {
        val context = itemView.context

        binding.apply {
            // 1. Données de base
            tvPlayerName.text = player.name
            tvPlayerRank.text = player.rank.name
            tvPlayerRank.setTextColor(ContextCompat.getColor(context, player.rank.color))
            tvPlayerId.text = context.getString(R.string.txt_player_id, player.id)
            tvPlayerLps.text = context.getString(R.string.txt_player_lps, player.lps)

            // 2. État du Pari
            setupBetUI(player, context)

            // 3. Expansion
            expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE
            root.setOnClickListener { onExpandClick() }

            // 4. Boutons d'actions
            btnVoteWin.text = player.formatBetButtonText(context, R.string.txt_win_title, player.nbWonBets)
            btnVoteLose.text = player.formatBetButtonText(context, R.string.txt_lose_title, player.nbLostBets)
            btnVoteWin.setOnClickListener {
                listener.onBetClick(player, true)
            }
            btnVoteLose.setOnClickListener {
                listener.onBetClick(player, false)
            }
        }
    }

    private fun setupBetUI(player: Player, context: Context) {
        binding.apply {
            if (!player.hasOnlineBet) {
                tvPlayerHasBetOnline.visibility = View.GONE
                tvPlayerHasBetOnline.clearAnimation()
                // Ici tu pourrais utiliser ta couleur #3F51B5FF si besoin
                vBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            } else {
                tvPlayerHasBetOnline.visibility = View.VISIBLE
                tvPlayerHasBetOnline.startAnimation(AnimationUtils.loadAnimation(context, R.anim.blink_rec))
                vBorder.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            }
        }
    }
}