package com.slayer.trackme.ui.auth.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.slayer.trackme.R
import com.slayer.trackme.common.createSpannableString
import com.slayer.trackme.databinding.FragmentWelcomeOptionsBinding

class WelcomeOptionsFragment : Fragment() {
    private var _binding: FragmentWelcomeOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeOptionsBinding.inflate(inflater, container, false)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeOptionsFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeOptionsFragment_to_registerFragment)
        }

        binding.tvWelcome.text = createSpannableString(
            10,
            17,
            getString(R.string.welcome_to_track_me),
            requireContext()
        )

        // Inflate the layout for this fragment
        return binding.root
    }
}