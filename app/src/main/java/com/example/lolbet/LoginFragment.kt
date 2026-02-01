package com.example.lolbet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lolbet.databinding.FragmentLoginBinding
import com.example.lolbet.viewmodel.AuthViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    // On utilise le View Binding pour éviter les findViewById
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        // 1. Aller vers l'inscription
        binding.tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_register)
        }

        // 2. Simuler une connexion réussie
        binding.btnLogin.setOnClickListener {
            // Ici tu mettrais ta logique Firebase ou API
            loginUser()
        }
    }

    private fun loginUser() {
        // Une fois connecté, on change d'Activity
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)

        // IMPORTANT : On ferme l'AuthActivity pour que l'utilisateur
        // ne revienne pas au login en appuyant sur "Retour"
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // On libère la mémoire
    }
}