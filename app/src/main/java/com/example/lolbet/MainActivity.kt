package com.example.lolbet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Afficher le premier fragment par défaut
        if (savedInstanceState == null) {
            loadFragment(ProfileFragment())
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
    }

    // Fonction utilitaire pour changer de fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}