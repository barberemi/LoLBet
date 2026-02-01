package com.example.lolbet

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lolbet.databinding.FragmentCompetitionBinding

class CompetitionFragment : Fragment() {

    // On prépare la variable binding
    private var _binding: FragmentCompetitionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialisation du binding
        _binding = FragmentCompetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // Très important : on libère le binding quand le fragment est détruit
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}