package com.example.lolbet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.lolbet.data.BetStatusEnum
import com.example.lolbet.databinding.FragmentProfileBinding
import com.example.lolbet.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.userData.observe(viewLifecycleOwner) { user ->
            val rank = userViewModel.getRank(user.rps)

            binding.tvUsername.text = user.name
            binding.tvUsernbBetWin.text = user.nbBetWin.toString()
            binding.tvUsernbBetLost.text = user.nbBetLost.toString()
            binding.tvUserlevel.text = getString(R.string.txt_level, user.level)
            binding.tvUserrps.text = getString(R.string.txt_rps, user.rps)
            // rank
            binding.tvUserrank.text = rank?.name ?: "Unranked"
            rank?.let {
                val colorInt = ContextCompat.getColor(requireContext(), it.color)
                binding.tvUserrank.setTextColor(colorInt)
                binding.tvUserstarRank.setColorFilter(colorInt)
            }
            // next rank
            val nextRank = userViewModel.getRpsNextRank(user.rps)
            if (nextRank == null) {
                binding.tvUsernextRank.visibility = View.GONE
            } else {
                binding.tvUsernextRank.visibility = View.VISIBLE
                binding.tvUsernextRank.text = getString(R.string.txt_rps_next_rank, nextRank.minRps)
            }
            // winrate
            val winrate = user.nbBetWin + user.nbBetLost
            if (winrate == 0) {
                binding.tvUserwinrate.text = 0.toString()
            } else {
                val rate = user.nbBetWin.toDouble() / winrate.toDouble() * 100.0
                val rateFormatted = String.format("%.2f", rate) // 2 chiffres après la virgule
                binding.tvUserwinrate.text = rateFormatted
            }
            // arrow winrate
            val lastNonPendingBet = user.getLastNonPendingBet()
            val arrowResId: Int
            val colorResId: Int
            when (lastNonPendingBet?.status) {
                BetStatusEnum.WON -> {
                    arrowResId = android.R.drawable.arrow_up_float
                    colorResId = android.R.color.holo_green_light   // ou une couleur de ton colors.xml
                }
                BetStatusEnum.LOST -> {
                    arrowResId = android.R.drawable.arrow_down_float
                    colorResId = android.R.color.holo_red_light     // ou une couleur de ton colors.xml
                }
                else -> {
                    arrowResId = android.R.drawable.arrow_up_float
                    colorResId = android.R.color.darker_gray        // ou une couleur de ton colors.xml
                }
            }
            binding.tvUserwinrateArrow.setImageResource(arrowResId)
            binding.tvUserwinrateArrow.setColorFilter(
                ContextCompat.getColor(requireContext(), colorResId)
            )
            // image de profil
            val resId = resources.getIdentifier(user.picture,"drawable", requireContext().packageName)
            if (resId != 0) {
                binding.tvUseravatar.setImageResource(resId)
            } else {
                // éventuelle image par défaut
                binding.tvUseravatar.setImageResource(R.drawable.xadxxx)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}