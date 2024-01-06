package com.slayer.trackme.ui.auth.register

import android.app.Activity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.slayer.common.ValidationUtil
import com.slayer.trackme.Utils.safeCall
import com.slayer.trackme.Utils.toast
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

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    lifecycleScope.launch {
                        val account = task.getResult(ApiException::class.java)!!
                        viewModel.tryLoginWithGoogle(account.idToken!!)

                        if (firebaseAuth.currentUser != null) {
                            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        }
                    }
                } catch (e: ApiException) {
                    Log.e(TAG, "Google sign in failed", e)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        initializeGso()

        initializeGoogleSignInClient()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        handleRegisterBtnClick()
        setupSignUpTextColor()

        binding.tvAlreadyHaveAccount.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnGoogle.setOnClickListener {
            safeCall(requireContext()) {
                googleSignInLauncher.launch(googleSignInClient.signInIntent)
            }
        }

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
                val confirmPassword = etConfirmPassword.text.toString().trim()

                if (areValidFields(email, password, confirmPassword)) return@setOnClickListener

                safeCall(requireContext()) {
                    lifecycleScope.launch {
                        viewModel.tryRegister(email, password)

                        if (viewModel.registerResult.value?.user != null) {
                            findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                        }
                        else {
                            toast(viewModel.handleSignUpWithEmailAndPasswordException())
                        }
                    }
                }
            }
        }
    }

    private fun areValidFields(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        binding.apply {
            if (!ValidationUtil.isValidEmailAddress(email)) {
                containerEmail.error =
                    getString(R.string.invalid_email_address_please_enter_a_valid_email)
                return true
            }

            if (!ValidationUtil.isValidPasswordLength(password)) {
                containerPassword.error = getString(R.string.invalid_password_length)
                return true
            }

            if (!ValidationUtil.arePasswordsEqual(password, confirmPassword)) {
                containerConfirmPassword.error = getString(R.string.password_mismatch)
                return true
            }
        }
        return false
    }

    private fun setupSignUpTextColor() {
        val fullText = getString(R.string.do_you_have_account_sign_in)

        // Locate the position of the specific word you want to highlight
        val startIndex = 20
        val endIndex = startIndex + 8

        // Create a SpannableString
        val spannableString = SpannableString(fullText)

        // Apply a different color to the specific word
        spannableString.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.md_theme_light_primary
                )
            ),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Set the SpannableString to the TextView
        binding.tvAlreadyHaveAccount.text = spannableString
    }

    private fun initializeGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun initializeGso() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
}