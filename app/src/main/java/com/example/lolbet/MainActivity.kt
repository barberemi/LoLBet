package com.example.lolbet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.lolbet.databinding.ActivityMainBinding
import com.example.lolbet.viewmodel.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigation

        // Afficher le premier fragment par défaut
        if (savedInstanceState == null) {
            loadFragment(CompetitionFragment())
        }

        // Écouter les clics sur le menu
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_competition -> loadFragment(CompetitionFragment())
                R.id.nav_live_bets -> loadFragment(LiveBetsFragment())
                R.id.nav_history -> loadFragment(HistoryFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
            }
            true
        }

        // OnClick listener pour le profil
        val btnProfile = binding.btnProfile
        btnProfile.setOnClickListener {
            openProfile()
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            val bottomNavListener = binding.bottomNavigation

            when (currentFragment) {
                is CompetitionFragment -> bottomNavListener.menu.findItem(R.id.nav_competition).isChecked = true
                is LiveBetsFragment -> bottomNavListener.menu.findItem(R.id.nav_live_bets).isChecked = true
                is HistoryFragment -> bottomNavListener.menu.findItem(R.id.nav_history).isChecked = true
                is SearchFragment -> bottomNavListener.menu.findItem(R.id.nav_search).isChecked = true
            }
        }

        // Observer les données de l'utilisateur pour mettre à jour l'UI
        userViewModel.userData.observe(this) { user ->
            binding.tvUsername.text = user.name
            binding.tvUserrps.text = getString(R.string.txt_rps, user.rps)
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
        val bottomNav = binding.bottomNavigation

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}