package com.example.lolbet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.adapter.OnPlayerBetListener
import com.example.lolbet.adapter.PlayerAdapter
import com.example.lolbet.data.Player
import com.example.lolbet.repository.PlayerRepository
import com.example.lolbet.viewmodel.PlayerViewModel
import com.example.lolbet.viewmodel.UserViewModel

class SearchFragment : Fragment() {
    private lateinit var adapter: PlayerAdapter
    private val userViewModel: UserViewModel by activityViewModels()
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = view.findViewById<SearchView>(R.id.searchViewPlayer)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvPlayerResults)

        // --- Initialisation de l'Adapter avec le listener ---
        adapter = PlayerAdapter(emptyList(), object : OnPlayerBetListener {
            override fun onBetClick(player: Player, isWinBet: Boolean) {
                // Sauvegarde
                viewModel.placeBet(player, isWinBet)
                // Notifie tous les observateurs que des choses ont changés
                userViewModel.refresh()
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // --- Observation des données ---
        // Dès que le ViewModel modifie la liste (après un pari), l'UI se met à jour
        viewModel.players.observe(viewLifecycleOwner) { updatedList ->
            // On récupère le texte actuellement écrit dans la searchView
            val currentQuery = searchView.query.toString()

            // On ne filtre (et donc on n'affiche) que si l'utilisateur a écrit quelque chose
            if (currentQuery.isNotBlank()) {
                filterList(updatedList, currentQuery)
            } else {
                adapter.submitList(emptyList())
            }
        }

        // --- Logique de recherche ---
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                // À chaque lettre tapée (ou effacée), on filtre
                filterList(viewModel.players.value ?: emptyList(), newText.orEmpty())
                return true
            }
        })
    }

    private fun filterList(allPlayers: List<Player>, query: String) {
        val filtered = if (query.trim().isEmpty()) {
            emptyList() // C'est ici qu'on décide de ne rien afficher au départ
        } else {
            val searchTerm = query.lowercase().trim()
            allPlayers.filter { it.name.lowercase().contains(searchTerm) }
        }
        adapter.submitList(filtered)
    }
}