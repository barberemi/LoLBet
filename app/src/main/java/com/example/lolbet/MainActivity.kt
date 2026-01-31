package com.example.lolbet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Afficher le premier fragment par défaut
        if (savedInstanceState == null) {
            loadFragment(AccountFragment())
        }

        // Ecouter les clics sur le menu
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_competition -> loadFragment(CompetitionFragment())
                R.id.nav_live_bets -> loadFragment(LiveBetsFragment())
                R.id.nav_history -> loadFragment(HistoryFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
            }
            true
        }

        // On Click listener pour le profil
        val btnProfile = findViewById<LinearLayout>(R.id.btn_profile)
        btnProfile.setOnClickListener {
            openProfile()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

            // Si on est revenu sur un fragment qui correspond à un onglet
            when (currentFragment) {
                is CompetitionFragment -> bottomNav.menu.findItem(R.id.nav_competition).isChecked = true
                is LiveBetsFragment -> bottomNav.menu.findItem(R.id.nav_live_bets).isChecked = true
                is HistoryFragment -> bottomNav.menu.findItem(R.id.nav_history).isChecked = true
                is SearchFragment -> bottomNav.menu.findItem(R.id.nav_search).isChecked = true
            }
        }
    }

    // Fonction utilitaire pour changer de fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    // Fonction pour ouvrir le profil utilisateur
    private fun openProfile() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // 1. On rend le menu "non cliquable" visuellement pour la sélection
        bottomNav.menu.setGroupCheckable(0, true, false)

        // 2. On décoche l'item actuellement sélectionné
        for (i in 0 until bottomNav.menu.size()) {
            bottomNav.menu.getItem(i).isChecked = false
        }

        // 3. On affiche le fragment Profile
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ProfileFragment())
            .addToBackStack(null) // Permet de revenir en arrière avec le bouton retour
            .commit()
    }
}