package com.example.lolbet.repository

import com.example.lolbet.R
import com.example.lolbet.data.Team

object TeamRepository {
    private val teams = listOf<Team>(
        Team(id = 1, initials = "KC", name = "Karmine Corp", logo = R.drawable.logo_kc),
        Team(id = 2, initials = "G2", name = "G2 Esports", logo = R.drawable.logo_g2),
        Team(id = 3, initials = "FNC", name = "Fnatic", logo = R.drawable.logo_fnc),
        Team(id = 4, initials = "T1", name = "T1 Esport", logo = R.drawable.logo_t1)
    )

    // La Map magique : elle transforme la liste en dictionnaire { "Nom": ObjetTeam }
    private val teamMap: Map<String, Team> = teams.associateBy { it.initials }

    /**
     * Récupère une équipe par son initial.
     * Retourne null si l'équipe n'existe pas ou si l'initials est null.
     */
    fun getTeamByInitials(initials: String?): Team? {
        if (initials == null) return null
        return teamMap[initials]
    }

    fun getAllTeams(): List<Team> = teams
}