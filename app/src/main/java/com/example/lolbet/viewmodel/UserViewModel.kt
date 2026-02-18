package com.example.lolbet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lolbet.data.Rank
import com.example.lolbet.data.User
import com.example.lolbet.repository.PlayerRepository
import com.example.lolbet.repository.RankRepository
import com.example.lolbet.repository.UserRepository

class UserViewModel: ViewModel() {
    private val _userData = MutableLiveData<User>()
    val userData: LiveData<User> get() = _userData

    init {
        _userData.value = UserRepository.getCurrentUser()
    }

    fun getRank(rps: Int): Rank? {
        return RankRepository.getRankByRps(rps)
    }

    fun getRpsNextRank(rps: Int): Rank? {
        return RankRepository.getNextRank(rps)
    }

    fun refresh() {
        val user = UserRepository.getCurrentUser()

        // On reconstruit la liste de paris en allant chercher les scores frais
        val refreshedBets = user.bets.map { bet ->
            if (bet.player != null) {
                // On récupère la version du joueur qui a les bons compteurs de votes
                val freshPlayer = PlayerRepository.getPlayerByName(bet.player.name)
                bet.copy(player = freshPlayer ?: bet.player)
            } else {
                bet
            }
        }

        // On publie l'utilisateur avec ses paris mis à jour
        _userData.value = user.copy(bets = refreshedBets)
    }
}