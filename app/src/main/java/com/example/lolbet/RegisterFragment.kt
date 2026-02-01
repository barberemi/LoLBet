package com.example.lolbet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.lolbet.databinding.FragmentRegisterBinding
import com.example.lolbet.viewmodel.AuthViewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    // On récupère le ViewModel rattaché à l'ACTIVITÉ (pas au fragment)
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)

        // On remplit automatiquement l'email si l'utilisateur l'avait tapé avant
        authViewModel.email.observe(viewLifecycleOwner) { typedEmail ->
            binding.etRegisterEmail.setText(typedEmail)
        }

        binding.tvBackToLogin.setOnClickListener {
            // Cela revient simplement au fragment précédent (LoginFragment)
            findNavController().popBackStack()
        }

        binding.btnRegister.setOnClickListener {
            // Logique d'inscription (Firebase / API)
            registerUser()
        }
    }

    private fun registerUser() {
        // Une fois inscrit, direction la MainActivity !
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}