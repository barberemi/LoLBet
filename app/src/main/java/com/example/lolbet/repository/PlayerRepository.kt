package com.example.lolbet.repository

import com.example.lolbet.data.Player

object PlayerRepository {
    private val players = listOf<Player>(
        Player(123,
            "Faker",
            9999,
            RankRepository.getRankByName("Challenger"),
            1500,
            true,
            "",
            120,
            2
        ),
        Player(456,
            "Caps",
            888,
            RankRepository.getRankByName("Grandmaster"),
            1250,
            false,
            "",
            100,
            55
        ),
        Player(789,
            "Rekkles",
            777,
            RankRepository.getRankByName("Master"),
            1000,
            false,
            "",
            85,
            68
        ),
        Player(556,
            "ShowMaker",
            666,
            RankRepository.getRankByName("Master"),
            955,
            false,
            "",
            22,
            12
        ),
        Player(666,
            "XadXXX",
            69,
            RankRepository.getRankByName("Iron III"),
            11,
            true,
            "",
            2,
            153
        ),
    )

    // La Map magique : elle transforme la liste en dictionnaire { "Nom": ObjetPlayer }
    private val playerMap: Map<String, Player> = players.associateBy { it.name }

    /**
     * Récupère une équipe par son initial.
     * Retourne null si l'équipe n'existe pas ou si l'initials est null.
     */
    fun getPlayerByName(name: String?): Player? {
        if (name == null) return null
        return playerMap[name]
    }

    fun getAllPlayers(): List<Player> = players // Todo : à garder pas trop gourmand ???
}