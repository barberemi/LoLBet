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

class SearchFragment : Fragment() {

    // TODO : remove from here
    val mockPlayers = listOf(
        Player(123, "Faker", 9999, "CHALENGER", 1500, true, "", 120, 2, 10000),
        Player(456, "Caps", 888, "GRANDMASTER", 1250, false, "", 100, 55, 5566),
        Player(789, "Rekkles", 777, "GRANDMASTER", 1000, false, "", 85, 68, 1235),
        Player(556, "ShowMaker", 666, "GRANDMASTER",955, false, "", 22, 12, 455),
        Player(666, "XadXXX", 69, "NOOB 5", 11, true, "", 2, 153, 9999),
    )

    private lateinit var adapter: PlayerAdapter
    private val allPlayers = mockPlayers  // ta liste mockée

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