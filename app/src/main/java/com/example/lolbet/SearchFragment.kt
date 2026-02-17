package com.example.lolbet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lolbet.adapter.PlayerAdapter
import com.example.lolbet.data.Player
import com.example.lolbet.repository.PlayerRepository

class SearchFragment : Fragment() {
    private lateinit var adapter: PlayerAdapter
    private val allPlayers = PlayerRepository.getAllPlayers()  // ta liste mockée

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_search, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = view.findViewById<SearchView>(R.id.searchViewPlayer)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvPlayerResults)

        adapter = PlayerAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                val text = newText.orEmpty().lowercase()

                val filtered = if (text.isBlank()) {
                    emptyList()
                } else {
                    allPlayers.filter { it.name.lowercase().contains(text) }
                }

                adapter.submitList(filtered)
                return true
            }
        })
    }
}