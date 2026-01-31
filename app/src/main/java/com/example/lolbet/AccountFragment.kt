package com.example.lolbet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.lolbet.R
import com.example.lolbet.databinding.FragmentAccountBinding
import kotlinx.coroutines.launch

class AccountFragment : Fragment(R.layout.fragment_account) {

//    private var _binding: FragmentAccountBinding? = null
//    private val binding get() = _binding!!

    // Simulant l'injection d'un RiotApiProvider
//    private val riotProvider = RiotApiProvider("VOTRE_CLE_API")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // On lie le fichier XML au code
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentAccountBinding.bind(view)
//
//        binding.btnSearch.setOnClickListener {
//            val input = binding.etRiotId.text.toString()
//            if (input.contains("#")) {
//                val parts = input.split("#")
//                searchAccount(parts[0], parts[1])
//            } else {
//                Toast.makeText(context, "Format invalide (Nom#TAG)", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

//    private fun searchAccount(name: String, tag: String) {
//        lifecycleScope.launch {
//            try {
//                // Appel au Provider (Ktor ou Retrofit)
//                val account = riotProvider.getAccountByRiotId("europe", name, tag)
//
//                binding.cardResult.visibility = View.VISIBLE
//                binding.tvGameName.text = account.gameName
//                binding.tvTagLine.text = "#${account.tagLine}"
//                binding.tvPuuid.text = "PUUID: ${account.puuid}"
//            } catch (e: Exception) {
//                Toast.makeText(context, "Erreur: Joueur non trouvé", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}