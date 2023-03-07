package com.example.trackme.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.trackme.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.button.setOnClickListener {

            // TODO : Create sign in button with
//            auth.createUserWithEmailAndPassword("akhaled.0075@gmail.com", "Project00f30!")
//                .addOnCompleteListener(requireActivity()) {
//                    if (!it.isSuccessful) {
//                        Log.d("a7a", "${it.exception?.message}")
//                        return@addOnCompleteListener
//                    }
//                    Log.d("a7a", "Success")
//                }
        }
        return binding.root
    }
}