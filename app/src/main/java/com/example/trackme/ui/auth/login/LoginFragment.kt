package com.example.trackme.ui.auth.login

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
import com.example.common_ui.Utils.toast
import com.example.trackme.R
import com.example.trackme.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val TAG = this.javaClass.simpleName

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

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
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
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

    private fun initializeGoogleSignInClient() {
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun initializeGso() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        setupSignUpTextColor()
        handleLoginBtnClick()

        binding.btnGoogle.setOnClickListener {
            googleSignInLauncher.launch(googleSignInClient.signInIntent)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setupSignUpTextColor() {
        val fullText = getString(R.string.don_t_have_account_sign_up)

        // Locate the position of the specific word you want to highlight
        val startIndex = 20
        val endIndex = startIndex + 7

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
        binding.textView6.text = spannableString
    }

    private fun handleLoginBtnClick() {
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim().lowercase()
                val password = etPassword.text.toString().trim()

                lifecycleScope.launch {
                    viewModel.tryLogin(email, password)

                    if (viewModel.loginResult.value?.user != null) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else {
                        toast(viewModel.handleSignInWithEmailAndPasswordException())
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}