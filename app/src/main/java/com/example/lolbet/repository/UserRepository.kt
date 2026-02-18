package com.example.lolbet.repository

import com.example.lolbet.data.Bet
import com.example.lolbet.data.BetForEnum
import com.example.lolbet.data.BetStatusEnum
import com.example.lolbet.data.User

object UserRepository {
    private var currentUser: User = User(
        id = 777,
        name = "Cromelu",
        email = "contact@exemple.com",
        password = "password123",
        picture = "cromelu",
        nbBetWin = 10,
        nbBetLost = 5,
        rps = 1500,
        level = 5,
        bets = listOf(
            Bet(1,
                TeamRepository.getTeamByInitials("FNC"),
                TeamRepository.getTeamByInitials("G2"),
                null,
                100,
                44,
                BetStatusEnum.PENDING,
                1738677600000L,
                BetForEnum.TEAM_A_WINNING,
                "LEC"
            ),
            Bet(2,
                null,
                null,
                PlayerRepository.getPlayerByName("XadXXX"),
                200,
                23,
                BetStatusEnum.PENDING,
                1738764000000L,
                BetForEnum.PLAYER_WINNING,
                null
            ),
            Bet(3,
                TeamRepository.getTeamByInitials("G2"),
                TeamRepository.getTeamByInitials("T1"),
                null,
                300,
                58,
                BetStatusEnum.PENDING,
                1738839600000L,
                BetForEnum.TEAM_B_WINNING,
                "LEC"
            ),
            Bet(4,
                TeamRepository.getTeamByInitials("FNC"),
                TeamRepository.getTeamByInitials("T1"),
                null,
                10,
                422,
                BetStatusEnum.WON,
                1738850400000L,
                BetForEnum.TEAM_A_WINNING,
                "LEC"
            ),
            Bet(5,
                null,
                null,
                PlayerRepository.getPlayerByName("XadXXX"),
                55,
                888,
                BetStatusEnum.LOST,
                1738857600000L,
                BetForEnum.PLAYER_LOSING,
                null
            ),
            Bet(6,
                TeamRepository.getTeamByInitials("G2"),
                TeamRepository.getTeamByInitials("T1"),
                null,
                10,
                422,
                BetStatusEnum.PENDING,
                1739703600000L,
                BetForEnum.TEAM_B_WINNING,
                "LEC"
            ),
            Bet(7,
                TeamRepository.getTeamByInitials("FNC"),
                TeamRepository.getTeamByInitials("T1"),
                null,
                10,
                422,
                BetStatusEnum.PENDING,
                1739012400000L,
                BetForEnum.TEAM_B_WINNING,
                "LEC"
            ),
        )
    )

    fun getCurrentUser(): User {
        val user = currentUser
        // On "rafraîchit" chaque pari en allant chercher le joueur tout neuf dans le PlayerRepository
        val refreshedBets = user.bets.map { bet ->
            if (bet.player != null) {
                val freshPlayer = PlayerRepository.getPlayerByName(bet.player.name)
                bet.copy(player = freshPlayer ?: bet.player)
            } else {
                bet
            }
        }
        return user.copy(bets = refreshedBets)
    }

    fun addBetToUser(newBet: Bet) {
        // On crée une nouvelle liste avec le nouveau pari au début (index 0)
        val updatedBets = currentUser.bets.toMutableList()
        updatedBets.add(0, newBet)

        // On met à jour l'utilisateur avec la nouvelle liste
        currentUser = currentUser.copy(bets = updatedBets)
    }
}