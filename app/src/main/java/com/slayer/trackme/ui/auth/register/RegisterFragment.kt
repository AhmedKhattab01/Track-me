package com.slayer.trackme.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.slayer.common.ValidationUtil
import com.slayer.trackme.R
import com.slayer.trackme.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)

        handleRegisterBtnClick()

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleRegisterBtnClick() {
        binding.apply {
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString().trim().lowercase()
                val password = etPassword.text.toString().trim()

                if (!ValidationUtil.isValidEmailAddress(email)) {
                    containerEmail.error = getString(R.string.invalid_email_address_please_enter_a_valid_email)
                    return@setOnClickListener
                }

                lifecycleScope.launch {
                    viewModel.tryRegister(email, password)

                    if (viewModel.registerResult.value?.user != null) {
                        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                    }
                    else {
                        //toast(viewModel.handleSignInWithEmailAndPasswordException())
                    }
                }
            }
        }
    }
}