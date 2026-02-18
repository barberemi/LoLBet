package com.example.lolbet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.data.BetStatusEnum
import com.example.lolbet.data.Player
import com.example.lolbet.repository.PlayerRepository
import com.example.lolbet.repository.UserRepository

class PlayerViewModel : ViewModel() {
    // On initialise directement avec la liste du Repository
    private val _players = MutableLiveData<List<Player>>(PlayerRepository.getAllPlayers())
    val players: LiveData<List<Player>> = _players

    fun placeBet(playerToUpdate: Player, isWinBet: Boolean) {
        val currentList = _players.value ?: return

        // 1. On prépare le joueur mis à jour (On fait le +1 ici)
        val updatedPlayer = playerToUpdate.copy(
            hasOnlineBet = true,
            nbWonBets = if (isWinBet) playerToUpdate.nbWonBets + 1 else playerToUpdate.nbWonBets,
            nbLostBets = if (!isWinBet) playerToUpdate.nbLostBets + 1 else playerToUpdate.nbLostBets
        )

        // 2. SAUVEGARDE RÉELLE
        PlayerRepository.updatePlayer(updatedPlayer)

        // On crée le Bet en utilisant TOUTES les infos de UPDATED_PLAYER
        val betForUser = Bet(
            id = (100..999).random(),
            player = updatedPlayer, // <--- UTILISE LE JOUEUR MIS À JOUR
            nbWinningBets = updatedPlayer.nbWonBets, // <--- UTILISE LE NOUVEAU SCORE
            nbLosingBets = updatedPlayer.nbLostBets, // <--- UTILISE LE NOUVEAU SCORE
            status = BetStatusEnum.PENDING,
            date = System.currentTimeMillis(),
            hasBetFor = if (isWinBet) BetForEnum.PLAYER_WINNING else BetForEnum.PLAYER_LOSING,
        )
        UserRepository.addBetToUser(betForUser)

        // 3. Mise à jour de l'UI pour l'écran de recherche
        val newList = currentList.map {
            if (it.id == updatedPlayer.id) updatedPlayer else it
        }
        _players.value = newList
    }
}