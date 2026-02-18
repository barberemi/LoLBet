package com.example.lolbet.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import com.example.lolbet.R
import com.example.lolbet.data.Player

/**
 * Formate le texte des boutons de vote avec un sous-titre plus petit pour le nombre de votes.
 */
fun Player.formatBetButtonText(context: Context, resId: Int, nbVotes: Int?): SpannableString {
    val title = context.getString(resId)
    val displayVotes = if (this.hasOnlineBet) nbVotes else null

    val fullText = displayVotes?.let {
        "$title\n${context.getString(R.string.txt_nb_votes, it)}"
    } ?: title

    return SpannableString(fullText).apply {
        if (displayVotes != null) {
            setSpan(
                RelativeSizeSpan(0.7f),
                title.length,
                length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}