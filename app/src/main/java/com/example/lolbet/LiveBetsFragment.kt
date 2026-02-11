package com.example.lolbet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lolbet.adapter.BetAdapter
import com.example.lolbet.data.BetStatusEnum
import com.example.lolbet.databinding.FragmentLiveBetsBinding
import com.example.lolbet.viewmodel.UserViewModel
import kotlin.getValue

class LiveBetsFragment : Fragment() {
    private var _binding: FragmentLiveBetsBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var betAdapter: BetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveBetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Configurer le RecyclerView
        betAdapter = BetAdapter(emptyList())
        binding.rvLiveBets.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = betAdapter
        }

        // 2. Observer les données
        userViewModel.userData.observe(viewLifecycleOwner) { user ->
            val pendingBets = user.bets.filter { it.status == BetStatusEnum.PENDING }
            if (pendingBets.isEmpty()) {
                binding.tvNoBets.visibility = View.VISIBLE
                binding.rvLiveBets.visibility = View.GONE
            } else {
                binding.tvNoBets.visibility = View.GONE
                binding.rvLiveBets.visibility = View.VISIBLE
                betAdapter.updateData(pendingBets)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}